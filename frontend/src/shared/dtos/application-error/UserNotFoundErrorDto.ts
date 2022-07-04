import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface UserNotFoundErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND> {}
