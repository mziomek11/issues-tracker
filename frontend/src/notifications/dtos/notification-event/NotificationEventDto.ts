import { NotificationEvent } from '@notifications/enums/notification-event';

export interface NotificationEventDto<TData> {
  event: NotificationEvent;
  data: TData;
}
