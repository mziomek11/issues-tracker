import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueCommentContentSetErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ISSUE_COMMENT_CONTENT_SET,
    HttpStatus.CONFLICT
  > {}
