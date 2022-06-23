import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export interface ApplicationErrorDto<
  TCode extends ApplicationErrorCode,
  TStatus extends HttpStatus
> {
  code: TCode;
  status: TStatus;
  message: string;
}
