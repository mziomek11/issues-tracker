import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface UserInvalidActivationTokenDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.USER_INVALID_ACTIVATION_TOKEN,
    HttpStatus.CONFLICT
  > {}
