import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface GenericEmailUnavailableErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE,
    HttpStatus.CONFLICT
  > {}
