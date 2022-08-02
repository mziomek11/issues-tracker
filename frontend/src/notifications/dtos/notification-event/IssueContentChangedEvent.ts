import { NotificationEventDto } from './NotificationEventDto';

export interface IssueContentChangedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
}

export interface IssueContentChangedEventDto
  extends NotificationEventDto<IssueContentChangedEventDataDto> {}
