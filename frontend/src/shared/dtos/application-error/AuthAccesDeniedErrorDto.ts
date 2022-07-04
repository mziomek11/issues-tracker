import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface AuthAccessDeniedErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.AUTH_ACCESS_DENIED, HttpStatus.FORBIDDEN> {}
