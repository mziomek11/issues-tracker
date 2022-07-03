import {
  NotificationEventDto,
  OrganizationCreatedEventDto,
} from '@notifications/dtos/notification-event';
import { NotificationEvent } from '@notifications/enums/notification-event';

type CallbackFn<TData> = (data: TData) => void;

type HandlerFn<TData> = (callback: CallbackFn<TData>) => SseHandler;

interface Callbacks {
  [NotificationEvent.ORGANIZATION_CREATED]?: CallbackFn<OrganizationCreatedEventDto>;
}
export interface SseHandler {
  onOrganizationCreatedEvent: HandlerFn<OrganizationCreatedEventDto>;
  handle: (sse: NotificationEventDto<NotificationEvent>) => void;
}

export const sseHandler = (callbacks: Callbacks = {}): SseHandler => {
  const handle = (sse: NotificationEventDto<NotificationEvent>): void => {
    const callback = callbacks[sse.event as NotificationEvent];

    if (callback) {
      callback(sse as any);
    }
  };
  return {
    onOrganizationCreatedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ORGANIZATION_CREATED]: callback }),
    handle,
  };
};
