import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueNameSetErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.ISSUE_NAME_SET, HttpStatus.CONFLICT> {}
