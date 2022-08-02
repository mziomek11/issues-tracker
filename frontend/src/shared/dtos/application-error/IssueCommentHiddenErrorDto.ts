import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueCommentHiddenErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.ISSUE_COMMENT_HIDDEN, HttpStatus.CONFLICT> {}
