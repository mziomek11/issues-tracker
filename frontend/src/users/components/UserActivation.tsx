import { useEffect, useState } from 'react';
import { AxiosError } from 'axios';
import { validate as uuidValidate } from 'uuid';
import { Badge, Spinner, Text, VStack } from '@chakra-ui/react';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { useActivate } from '@users/hooks/api';
import { UserActivationParams } from '@users/types/activation';

interface ActivationStatusDto {
  validUuids: boolean;
  loading: boolean;
  error: boolean;
  errorData?: ApplicationErrorDto<ApplicationErrorCode, HttpStatus>;
}
export const UserActivation: React.FC<UserActivationParams> = ({ userId, activationToken }) => {
  const { mutate: activate } = useActivate();
  const isUuidValid = uuidValidate(userId) && uuidValidate(activationToken);
  const [activationStatus, setActivationStatus] = useState<ActivationStatusDto>({
    validUuids: isUuidValid,
    loading: isUuidValid,
    error: false,
  });

  const handleSuccess = (): void => {
    setActivationStatus((state) => ({
      ...state,
      loading: false,
      error: false,
    }));
  };

  const handleError = (error: AxiosError<ApplicationErrorDto<any, any>, unknown>): void =>
    applicationErrorHandler<UserActivationParams>()
      .onGenericValidationFailed(({ details }) => console.log(details))
      .onUserInvalidActivationToken((err) => {
        setActivationStatus((state) => ({
          ...state,
          loading: false,
          error: true,
          errorData: { ...err },
        }));
      })
      .onUserAlreadyActivated((err) => {
        setActivationStatus((state) => ({
          ...state,
          loading: false,
          error: true,
          errorData: { ...err },
        }));
      })
      .onUserDoesNotExist((err) => {
        setActivationStatus((state) => ({
          ...state,
          loading: false,
          error: true,
          errorData: { ...err },
        }));
      })
      .handleAxiosError(error);

  const handleActivation = (): void => {
    activate({ userId, activationToken }, { onError: handleError, onSuccess: handleSuccess });
  };

  useEffect(() => {
    if (isUuidValid) {
      handleActivation();
    }
  }, []);

  return (
    <VStack>
      {activationStatus.validUuids ? (
        activationStatus.loading ? (
          <Spinner />
        ) : activationStatus.error ? (
          <>
            <Badge colorScheme="red">Error</Badge>
            <Text fontSize="md">{activationStatus.errorData?.message}</Text>
          </>
        ) : (
          <>
            <Badge colorScheme="green">Activated</Badge>
            <Text fontSize="md">User activated</Text>
          </>
        )
      ) : (
        <>
          <Badge colorScheme="yellow">Warning</Badge>
          <Text fontSize="md">These are not valid uuids</Text>
        </>
      )}
    </VStack>
  );
};
