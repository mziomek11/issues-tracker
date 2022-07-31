export enum ApplicationErrorCode {
  GENERIC_VALIDATION_FAILED = 'GENERIC_2',
  GENERIC_EMAIL_UNAVAILABLE = 'GENERIC_3',
  USER_INVALID_ACTIVATION_TOKEN = 'USER_1',
  USER_ALREADY_ACTIVATED = 'USER_2',
  USER_NOT_FOUND = 'USER_3',
  AUTH_ACCESS_DENIED = 'AUTH_1',
  AUTH_INVALID_CREDENTIALS = 'AUTH_2',
  AUTH_INVALID_JWT = 'AUTH_3',
  ORGANIZATION_NOT_FOUND = 'ORGANIZATION_1',
  ORGANIZATION_OWNER_INVALID = 'ORGANIZATION_2',
  ORGANIZATION_INVITATION_NOT_FOUND = 'ORGANIZATION_3',
  ORGANIZATION_INVITATION_ALREADY_PRESENT = 'ORGANIZATION_4',
  ORGANIZATION_MEMBER_ALREADY_PRESENT = 'ORGANIZATION_5',
  ORGANIZATION_ACCESS_DENIED = 'ORGANIZATION_6',
  ORGANIZATION_PROJECT_NOT_FOUND = 'ORGANIZATION_7',
  ISSUE_NOT_FOUND = 'ISSUE_1',
}
