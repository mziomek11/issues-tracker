import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface AuthInvalidCredentialsErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.AUTH_INVALID_CREDENTIALS, HttpStatus.CONFLICT> {}
