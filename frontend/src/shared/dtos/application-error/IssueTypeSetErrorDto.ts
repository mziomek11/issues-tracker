import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueTypeSetErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.ISSUE_TYPE_SET, HttpStatus.CONFLICT> {}
