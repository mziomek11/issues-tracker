import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorResponseDto } from './ApplicationErrorResponseDto';

export interface GenericValidationFailedErrorDto<TFields extends Record<string, unknown>>
  extends ApplicationErrorResponseDto<
    ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE,
    HttpStatus.BAD_REQUEST
  > {
  details: Partial<Record<keyof TFields, string[]>>;
}
