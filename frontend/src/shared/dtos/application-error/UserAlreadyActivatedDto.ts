import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorResponseDto } from './ApplicationErrorResponseDto';

export interface UserAlreadyActivatedDto
  extends ApplicationErrorResponseDto<
    ApplicationErrorCode.USER_INVALID_ACTIVATION_TOKEN,
    HttpStatus.CONFLICT
  > {}
