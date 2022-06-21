import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface AuthInvalidJwtDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.AUTH_INVALID_JWT,
    HttpStatus.CONFLICT
  > {}
