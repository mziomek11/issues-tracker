import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface AuthAccessDeniedDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.AUTH_ACCESS_DENIED,
    HttpStatus.CONFLICT
  > {}
