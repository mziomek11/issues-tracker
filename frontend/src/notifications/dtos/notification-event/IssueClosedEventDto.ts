import { NotificationEventDto } from './NotificationEventDto';

export interface IssueClosedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
}

export interface IssueClosedEventDto extends NotificationEventDto<IssueClosedEventDataDto> {}
