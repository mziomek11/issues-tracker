import { AxiosError } from 'axios';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import {
  ApplicationErrorDto,
  AuthAccessDeniedErrorDto,
  AuthInvalidCredentialsErrorDto,
  AuthInvalidJwtErrorDto,
  GenericEmailUnavailableErrorDto,
  GenericValidationFailedErrorDto,
  OrganizationNotFoundErrorDto,
  OrganizationOwnerInvalidErrorDto,
  UserAlreadyActivatedErrorDto,
  UserInvalidActivationTokenErrorDto,
  UserNotFoundErrorDto,
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
  [ApplicationErrorCode.USER_INVALID_ACTIVATION_TOKEN]?: CallbackFn<UserInvalidActivationTokenErrorDto>;
  [ApplicationErrorCode.USER_ALREADY_ACTIVATED]?: CallbackFn<UserAlreadyActivatedErrorDto>;
  [ApplicationErrorCode.USER_NOT_FOUND]?: CallbackFn<UserNotFoundErrorDto>;
  [ApplicationErrorCode.AUTH_ACCESS_DENIED]?: CallbackFn<AuthAccessDeniedErrorDto>;
  [ApplicationErrorCode.AUTH_INVALID_CREDENTIALS]?: CallbackFn<AuthInvalidCredentialsErrorDto>;
  [ApplicationErrorCode.AUTH_INVALID_JWT]?: CallbackFn<AuthInvalidJwtErrorDto>;
  [ApplicationErrorCode.ORGANIZATION_NOT_FOUND]?: CallbackFn<OrganizationNotFoundErrorDto>;
  [ApplicationErrorCode.ORGANIZATION_OWNER_INVALID]?: CallbackFn<OrganizationOwnerInvalidErrorDto>;
}

export interface ApplicationErrorHandler<TFields extends Record<string, unknown>> {
  onGenericValidationFailed: HandlerFn<GenericValidationFailedErrorDto<TFields>, TFields>;
  onGenericEmailUnavailable: HandlerFn<GenericEmailUnavailableErrorDto, TFields>;
  onUserInvalidActivationToken: HandlerFn<UserInvalidActivationTokenErrorDto, TFields>;
  onUserAlreadyActivated: HandlerFn<UserAlreadyActivatedErrorDto, TFields>;
  onUserNotFound: HandlerFn<UserNotFoundErrorDto, TFields>;
  onAuthAccessDenied: HandlerFn<AuthAccessDeniedErrorDto, TFields>;
  onAuthInvalidCredentials: HandlerFn<AuthInvalidCredentialsErrorDto, TFields>;
  onAuthInvalidJwt: HandlerFn<AuthInvalidJwtErrorDto, TFields>;
  onOrganizationNotFound: HandlerFn<OrganizationNotFoundErrorDto, TFields>;
  onOrganizationOwnerInvalid: HandlerFn<OrganizationOwnerInvalidErrorDto, TFields>;
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
    onUserNotFound: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.USER_NOT_FOUND]: callback,
      }),
    onAuthAccessDenied: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.AUTH_ACCESS_DENIED]: callback,
      }),
    onAuthInvalidCredentials: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.AUTH_INVALID_CREDENTIALS]: callback,
      }),
    onAuthInvalidJwt: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.AUTH_INVALID_JWT]: callback,
      }),
    onOrganizationNotFound: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.ORGANIZATION_NOT_FOUND]: callback,
      }),
    onOrganizationOwnerInvalid: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.ORGANIZATION_OWNER_INVALID]: callback,
      }),
    handleAxiosError: (error): void => {
      if (!error.response?.data?.code) {
        return;
      }
      handleError(error.response.data);
    },
  };
};
