import { NotificationEventDto } from './NotificationEventDto';

export interface IssueTypeChangedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
}

export interface IssueTypeChangedEventDto
  extends NotificationEventDto<IssueTypeChangedEventDataDto> {}
