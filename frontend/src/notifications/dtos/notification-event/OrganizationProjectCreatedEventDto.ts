import { NotificationEventDto } from '@notifications/dtos/notification-event/NotificationEventDto';

export interface OrganizationProjectCreatedEventDto
  extends NotificationEventDto<Record<string, string>> {
  data: {
    organizationId: string;
    projectId: string;
  };
}
