import { NotificationEventDto } from './NotificationEventDto';

export interface IssueCommentHiddenEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
  commentId: string;
}

export interface IssueCommentHiddenEventDto
  extends NotificationEventDto<IssueCommentHiddenEventDataDto> {}
