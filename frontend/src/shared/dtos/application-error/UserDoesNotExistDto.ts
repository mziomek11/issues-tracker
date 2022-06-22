import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorResponseDto } from './ApplicationErrorResponseDto';

export interface UserDoesNotExistDto
  extends ApplicationErrorResponseDto<
    ApplicationErrorCode.USER_DOES_NOT_EXIST,
    HttpStatus.NOT_FOUND
  > {}
