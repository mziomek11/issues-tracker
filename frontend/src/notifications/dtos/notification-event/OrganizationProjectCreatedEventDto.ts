import { NotificationEventDto } from '@notifications/dtos/notification-event/NotificationEventDto';

export interface OrganizationProjectCreatedEventDataDto {
  organizationId: string;
  projectId: string;
}
export interface OrganizationProjectCreatedEventDto
  extends NotificationEventDto<OrganizationProjectCreatedEventDataDto> {}
