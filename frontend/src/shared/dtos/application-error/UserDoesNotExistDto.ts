import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface UserDoesNotExistDto
  extends ApplicationErrorDto<ApplicationErrorCode.USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND> {}
