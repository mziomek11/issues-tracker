import { AxiosError } from 'axios';
import { isObject, isString } from 'lodash';
import { validate as uuidValidate } from 'uuid';
import { useEffect, useState } from 'react';
import { Badge, Spinner, Table, TableContainer, Td, Text, Tr, VStack } from '@chakra-ui/react';
import { ApplicationErrorResponseDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { useActivate } from '@users/hooks/api';
import { UserActivationParams } from '@users/types/activation';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { mapValidationErrors } from '@shared/mappers/application-error';

enum ActivationState {
  LOADING,
  ERROR,
  SUCCESS,
  INVALID_UUID,
}
interface ActivationStatus {
  state: ActivationState;
  message: string | Record<string, string>;
}
interface UserActivationProps extends UserActivationParams {}

export const UserActivation: React.FC<UserActivationProps> = ({ userId, activationToken }) => {
  const { mutate: activate } = useActivate();
  const isUuidValid = uuidValidate(userId) && uuidValidate(activationToken);
  const [activationStatus, setActivationStatus] = useState<ActivationStatus>({
    state: ActivationState.LOADING,
    message: '',
  });

  const handleSuccess = (): void => {
    setActivationStatus({ state: ActivationState.SUCCESS, message: '' });
  };

  const handleAppliactionError = (message: string | Record<string, string>): void => {
    setActivationStatus({ state: ActivationState.ERROR, message });
  };
  const handleError = (
    error: AxiosError<ApplicationErrorResponseDto<ApplicationErrorCode, any>, unknown>
  ): void =>
    applicationErrorHandler<UserActivationParams>()
      .onGenericValidationFailed(({ details }) =>
        handleAppliactionError(mapValidationErrors(details))
      )
      .onUserInvalidActivationToken((err) => handleAppliactionError(err.message))
      .onUserAlreadyActivated((err) => handleAppliactionError(err.message))
      .onUserDoesNotExist((err) => handleAppliactionError(err.message))
      .handleAxiosError(error);

  const handleActivation = (): void => {
    activate({ userId, activationToken }, { onError: handleError, onSuccess: handleSuccess });
  };

  useEffect(() => {
    if (isUuidValid) {
      setActivationStatus({ state: ActivationState.LOADING, message: '' });
      handleActivation();
    } else {
      setActivationStatus({ state: ActivationState.INVALID_UUID, message: '' });
    }
  }, []);

  return (
    <VStack>
      {activationStatus.state === ActivationState.LOADING && <Spinner />}
      {activationStatus.state === ActivationState.ERROR && (
        <>
          <Badge colorScheme="red">Error</Badge>
          {isString(activationStatus.message) && (
            <Text fontSize="md">{activationStatus.message}</Text>
          )}
          {isObject(activationStatus.message) && (
            <TableContainer>
              <Table>
                {Object.keys(activationStatus.message).map((key) => (
                  <Tr>
                    <Td>{key}</Td>
                    <Td>{activationStatus.message[key as keyof unknown]}</Td>
                  </Tr>
                ))}
              </Table>
            </TableContainer>
          )}
        </>
      )}
      {activationStatus.state === ActivationState.SUCCESS && (
        <>
          <Badge colorScheme="green">Activated</Badge>
          <Text fontSize="md">User activated</Text>
        </>
      )}
      {activationStatus.state === ActivationState.INVALID_UUID && (
        <>
          <Badge colorScheme="yellow">Warning</Badge>
          <Text fontSize="md">These are not valid uuids</Text>
        </>
      )}
    </VStack>
  );
};
