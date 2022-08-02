import { NotificationEventDto } from './NotificationEventDto';

export interface IssueCommentVotedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
  commentId: string;
}

export interface IssueCommentVotedEventDto
  extends NotificationEventDto<IssueCommentVotedEventDataDto> {}
