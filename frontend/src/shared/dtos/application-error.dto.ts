export enum ApplicationErrorCode {
  GENERIC_UNNAMED_ERROR = 'GENERIC_1',
  GENERIC_VALIDATION_FAILED = 'GENERIC_2',
  GENERIC_EMAIL_UNAVAILABLE = 'GENERIC_3',
}

export const applicationErrors: Record<ApplicationErrorCode, string> = {
  GENERIC_1: 'Unnamed error',
  GENERIC_2: 'Validation failed',
  GENERIC_3: 'This email is unavailable',
};
