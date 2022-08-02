import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueCommentNotFoundErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.ISSUE_COMMENT_NOT_FOUND, HttpStatus.NOT_FOUND> {}
