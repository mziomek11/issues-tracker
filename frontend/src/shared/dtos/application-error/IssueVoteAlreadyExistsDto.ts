import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface IssueVoteAlreadyExistsErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ISSUE_VOTE_ALREADY_EXISTS,
    HttpStatus.CONFLICT
  > {}
