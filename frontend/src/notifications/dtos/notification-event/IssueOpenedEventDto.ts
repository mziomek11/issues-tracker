import { NotificationEventDto } from './NotificationEventDto';

export interface IssueOpenedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
}

export interface IssueOpenedEventDto extends NotificationEventDto<IssueOpenedEventDataDto> {}
