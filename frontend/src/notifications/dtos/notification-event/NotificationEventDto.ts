import { NotificationEvent } from '@notifications/enums/notification-event';

export interface NotificationEventDto<TEvent extends NotificationEvent> {
  event: TEvent;
  data: any;
  id?: string;
}
