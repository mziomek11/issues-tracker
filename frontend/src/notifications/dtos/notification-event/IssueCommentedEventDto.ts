import { NotificationEventDto } from './NotificationEventDto';

export interface IssueCommentedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
  commentId: string;
}

export interface IssueCommentedEventDto extends NotificationEventDto<IssueCommentedEventDataDto> {}
