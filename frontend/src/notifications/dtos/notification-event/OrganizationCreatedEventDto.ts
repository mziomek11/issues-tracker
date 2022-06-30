import { NotificationEvent } from '@notifications/enums/notification-event';
import { NotificationEventDto } from './NotificationEventDto';

export interface OrganizationCreatedEventDto extends NotificationEventDto<NotificationEvent> {
  data: {
    organizationId: string;
  };
}
