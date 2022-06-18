import { ApplicationErrorCode } from '../dtos/application-error.dto';

export const applicationErrorHandler = (code: ApplicationErrorCode): ApplicationErrorCode | null=> {
  if (code === ApplicationErrorCode.GENERIC_UNNAMED_ERROR) return ApplicationErrorCode.GENERIC_UNNAMED_ERROR
  else if (code === ApplicationErrorCode.GENERIC_VALIDATION_FAILED) return ApplicationErrorCode.GENERIC_VALIDATION_FAILED
  else if (code === ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE) return ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE
  else return null
};
