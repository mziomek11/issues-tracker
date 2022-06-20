import { AxiosError } from 'axios';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import {
  ApplicationErrorDto,
  GenericEmailUnavailableErrorDto,
  GenericValidationFailedErrorDto,
  UserInvalidActivationTokenDto,
  UserDoesNotExistDto,
  UserAlreadyActivatedDto,
} from '@shared/dtos/application-error';

type CallbackFn<TData> = (data: TData) => void;

type HandlerFn<TData, TFields extends Record<string, unknown>> = (
  callback: CallbackFn<TData>
) => ApplicationErrorHandler<TFields>;

interface Callbacks<TFields extends Record<string, unknown>> {
  [ApplicationErrorCode.GENERIC_VALIDATION_FAILED]?: CallbackFn<
    GenericValidationFailedErrorDto<TFields>
  >;
  [ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE]?: CallbackFn<GenericEmailUnavailableErrorDto>;
  [ApplicationErrorCode.USER_INVALID_ACTIVATION_TOKEN]?: CallbackFn<UserInvalidActivationTokenDto>;
  [ApplicationErrorCode.USER_ALREADY_ACTIVATED]?: CallbackFn<UserAlreadyActivatedDto>;
  [ApplicationErrorCode.USER_DOES_NOT_EXIST]?: CallbackFn<UserDoesNotExistDto>;
}

export interface ApplicationErrorHandler<TFields extends Record<string, unknown>> {
  onGenericValidationFailed: HandlerFn<GenericValidationFailedErrorDto<TFields>, TFields>;
  onGenericEmailUnavailable: HandlerFn<GenericEmailUnavailableErrorDto, TFields>;
  onUserInvalidActivationToken: HandlerFn<UserInvalidActivationTokenDto, TFields>;
  onUserAlreadyActivated: HandlerFn<UserAlreadyActivatedDto, TFields>;
  onUserDoesNotExist: HandlerFn<UserDoesNotExistDto, TFields>;
  handleAxiosError: (error: AxiosError<ApplicationErrorDto<any, any>, unknown>) => void;
}

export const applicationErrorHandler = <TFields extends Record<string, any>>(
  callbacks: Callbacks<TFields> = {}
): ApplicationErrorHandler<TFields> => {
  const handleError = (error: ApplicationErrorDto<any, any>): void => {
    const callback = callbacks[error.code as ApplicationErrorCode];

    if (callback) {
      callback(error as any);
    }
  };

  return {
    onGenericValidationFailed: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.GENERIC_VALIDATION_FAILED]: callback,
      }),
    onGenericEmailUnavailable: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE]: callback,
      }),
    onUserInvalidActivationToken: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.USER_INVALID_ACTIVATION_TOKEN]: callback,
      }),
    onUserAlreadyActivated: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.USER_ALREADY_ACTIVATED]: callback,
      }),
    onUserDoesNotExist: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.USER_DOES_NOT_EXIST]: callback,
      }),
    handleAxiosError: (error): void => {
      if (!error.response?.data?.code) {
        return;
      }

      handleError(error.response.data);
    },
  };
};
