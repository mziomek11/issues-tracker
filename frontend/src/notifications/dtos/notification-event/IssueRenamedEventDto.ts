import { NotificationEventDto } from './NotificationEventDto';

export interface IssueRenamedEventDataDto {
  organizationId: string;
  projectId: string;
  issueId: string;
  memberId: string;
}

export interface IssueRenamedEventDto extends NotificationEventDto<IssueRenamedEventDataDto> {}
