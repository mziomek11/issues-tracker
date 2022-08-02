import { NotificationEventDto } from './NotificationEventDto';

export interface IssueCommentContentChangedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
  commentId: string;
}

export interface IssueCommentContentChangedEventDto
  extends NotificationEventDto<IssueCommentContentChangedEventDataDto> {}
