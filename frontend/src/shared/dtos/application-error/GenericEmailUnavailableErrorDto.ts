import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorResponseDto } from './ApplicationErrorResponseDto';

export interface GenericEmailUnavailableErrorDto
  extends ApplicationErrorResponseDto<
    ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE,
    HttpStatus.CONFLICT
  > {}
