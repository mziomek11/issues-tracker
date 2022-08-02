import { NotificationEventDto } from './NotificationEventDto';

export interface IssueVotedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
}

export interface IssueVotedEventDto extends NotificationEventDto<IssueVotedEventDataDto> {}
