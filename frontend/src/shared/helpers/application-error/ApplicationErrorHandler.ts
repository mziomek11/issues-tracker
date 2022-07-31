import { AxiosError } from 'axios';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import {
  ApplicationErrorDto,
  AuthAccessDeniedErrorDto,
  AuthInvalidCredentialsErrorDto,
  AuthInvalidJwtErrorDto,
  GenericEmailUnavailableErrorDto,
  GenericValidationFailedErrorDto,
  IssueNotFoundErrorDto,
  OrganizationAccessDeniedErrorDto,
  OrganizationInvitationAlreadyPresentErrorDto,
  OrganizationInvitationNotFoundErrorDto,
  OrganizationMemberAlreadyPresentErrorDto,
  OrganizationNotFoundErrorDto,
  OrganizationOwnerInvalidErrorDto,
  OrganizationProjectNotFoundErrorDto,
  UserAlreadyActivatedErrorDto,
  UserInvalidActivationTokenErrorDto,
  UserNotFoundErrorDto,
} from '@shared/dtos/application-error';
import { HttpStatus } from '@shared/enums/http';

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
  [ApplicationErrorCode.ORGANIZATION_INVITATION_NOT_FOUND]?: CallbackFn<OrganizationInvitationNotFoundErrorDto>;
  [ApplicationErrorCode.ORGANIZATION_INVITATION_ALREADY_PRESENT]?: CallbackFn<OrganizationInvitationAlreadyPresentErrorDto>;
  [ApplicationErrorCode.ORGANIZATION_MEMBER_ALREADY_PRESENT]?: CallbackFn<OrganizationMemberAlreadyPresentErrorDto>;
  [ApplicationErrorCode.ORGANIZATION_ACCESS_DENIED]?: CallbackFn<OrganizationAccessDeniedErrorDto>;
  [ApplicationErrorCode.ORGANIZATION_PROJECT_NOT_FOUND]?: CallbackFn<OrganizationProjectNotFoundErrorDto>;
  [ApplicationErrorCode.ISSUE_NOT_FOUND]?: CallbackFn<IssueNotFoundErrorDto>;
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
  onOrganizationInvitationNotFound: HandlerFn<OrganizationInvitationNotFoundErrorDto, TFields>;
  onOrganizationInvitationAlreadyPresent: HandlerFn<
    OrganizationInvitationAlreadyPresentErrorDto,
    TFields
  >;
  onOrganizationMemberAlreadyPresent: HandlerFn<OrganizationMemberAlreadyPresentErrorDto, TFields>;
  onOrganizationAccessDenied: HandlerFn<OrganizationAccessDeniedErrorDto, TFields>;
  onOrganizationProjectNotFound: HandlerFn<OrganizationProjectNotFoundErrorDto, TFields>;
  onIssueNotFound: HandlerFn<IssueNotFoundErrorDto, TFields>;
  handleAxiosError: (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => void;
}

export const applicationErrorHandler = <TFields extends Record<string, any>>(
  callbacks: Callbacks<TFields> = {}
): ApplicationErrorHandler<TFields> => {
  const handleError = (error: ApplicationErrorDto<ApplicationErrorCode, HttpStatus>): void => {
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
    onOrganizationInvitationNotFound: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.ORGANIZATION_INVITATION_NOT_FOUND]: callback,
      }),
    onOrganizationInvitationAlreadyPresent: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.ORGANIZATION_INVITATION_ALREADY_PRESENT]: callback,
      }),
    onOrganizationMemberAlreadyPresent: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.ORGANIZATION_MEMBER_ALREADY_PRESENT]: callback,
      }),
    onOrganizationAccessDenied: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.ORGANIZATION_ACCESS_DENIED]: callback,
      }),
    onOrganizationProjectNotFound: (callback) =>
      applicationErrorHandler({
        ...callbacks,
        [ApplicationErrorCode.ORGANIZATION_PROJECT_NOT_FOUND]: callback,
      }),
    onIssueNotFound: (callback) =>
      applicationErrorHandler({ ...callbacks, [ApplicationErrorCode.ISSUE_NOT_FOUND]: callback }),
    handleAxiosError: (error): void => {
      if (!error.response?.data?.code) {
        return;
      }
      handleError(error.response.data);
    },
  };
};
