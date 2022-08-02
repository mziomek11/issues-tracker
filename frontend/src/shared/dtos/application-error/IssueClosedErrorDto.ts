import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueClosedErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.ISSUE_CLOSED, HttpStatus.CONFLICT> {}
