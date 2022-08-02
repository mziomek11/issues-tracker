import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueContentSetErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.ISSUE_CONTENT_SET, HttpStatus.CONFLICT> {}
