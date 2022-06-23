import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { validate as uuidValidate } from 'uuid';
import { Badge, Spinner, Text, VStack } from '@chakra-ui/react';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { useActivate } from '@users/hooks/api';
import { UserActivationParams } from '@users/types/activation';
import { ApplicationErrorCode } from '@shared/enums/error-code';

interface UserActivationProps extends UserActivationParams {}

export const UserActivation: React.FC<UserActivationProps> = ({ userId, activationToken }) => {
  const { mutate: activate, isLoading, isError, isSuccess } = useActivate();
  const isUuidValid = uuidValidate(userId) && uuidValidate(activationToken);
  const [activationErrorMessage, setActivationErrorMessage] = useState<string>('');

  const handleAppliactionError = ({
    message,
  }: ApplicationErrorDto<ApplicationErrorCode, any>): void => {
    setActivationErrorMessage(message);
  };
  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, any>, unknown>
  ): void =>
    applicationErrorHandler<UserActivationParams>()
      .onUserInvalidActivationToken((err) => handleAppliactionError(err))
      .onUserAlreadyActivated((err) => handleAppliactionError(err))
      .onUserDoesNotExist((err) => handleAppliactionError(err))
      .handleAxiosError(error);

  const handleActivation = (): void => {
    activate({ userId, activationToken }, { onError: handleError });
  };

  useEffect(() => {
    if (isUuidValid) {
      handleActivation();
    }
  }, []);

  return (
    <VStack>
      {isLoading && <Spinner />}
      {isError && (
        <>
          <Badge colorScheme="red">Error</Badge>
          <Text fontSize="md">{activationErrorMessage}</Text>
        </>
      )}
      {isSuccess && (
        <>
          <Badge colorScheme="green">Activated</Badge>
          <Text fontSize="md">User activated</Text>
        </>
      )}
      {!isUuidValid && (
        <>
          <Badge colorScheme="yellow">Warning</Badge>
          <Text fontSize="md">These are not valid uuids</Text>
        </>
      )}
    </VStack>
  );
};
