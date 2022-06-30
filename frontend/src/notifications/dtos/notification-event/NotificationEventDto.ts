import { EventSourceMessage } from '@microsoft/fetch-event-source';
import { NotificationEvent } from '@notifications/enums/notification-event';

export interface NotificationEventDto<TEvent extends NotificationEvent> extends EventSourceMessage {
  event: TEvent;
  data: any;
}
