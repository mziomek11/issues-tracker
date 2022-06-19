import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { ActivateUserDto, ActivationTokenDto } from '@users/dtos';
import { useActivate } from '@users/hooks/api';
import { AxiosError } from 'axios';
import { validate as uuidValidate } from 'uuid';

interface UserActivationProps {
  userId: string;
  activationToken: string;
}
export const UserActivation: React.FC<UserActivationProps> = ({ userId, activationToken }) => {
  const { mutate: activate } = useActivate();
  const isUuidValid = uuidValidate(userId) && uuidValidate(activationToken);

  const handleSuccess = () => {};

  const handleError = (error: AxiosError<ApplicationErrorDto<any, any>, unknown>): void =>
    applicationErrorHandler<ActivateUserDto<ActivationTokenDto>>()
      .onGenericValidationFailed(({ details }) => console.log(details))
      .onUserDoesNotExist((err) => console.log(err))
      .onUserInvalidActivationToken((err) => console.log(err))
      .handleAxiosError(error);
  const handleActivation = () => {
    activate(
      { userId, activationToken: { activationToken } },
      { onError: handleError, onSuccess: handleSuccess }
    );
  };
  if (isUuidValid) {
    handleActivation();
  }
  return <>{isUuidValid ? 'Activating..' : 'Invalid user or activation token'}</>;
};
