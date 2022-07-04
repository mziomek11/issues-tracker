import { NotificationEvent } from '@notifications/enums/notification-event';
import { NotificationEventDto } from '@notifications/dtos/notification-event/NotificationEventDto';

export interface OrganizationProjectCreatedEventDto
  extends NotificationEventDto<NotificationEvent, Record<string, string>> {
  data: {
    organizationId: string;
    projectId: string;
  };
}
