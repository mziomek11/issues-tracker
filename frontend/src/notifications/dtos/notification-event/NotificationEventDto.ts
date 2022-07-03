import { NotificationEvent } from '@notifications/enums/notification-event';

export interface NotificationEventDto<
  TEvent extends NotificationEvent,
  TData extends Record<string, unknown>
> {
  event: TEvent;
  data: TData;
}
