import {
  NotificationEventDto,
  OrganizationCreatedEventDto,
} from '@notifications/dtos/notification-event';
import { NotificationEvent } from '@notifications/enums/notification-event';

type CallbackFn<TData> = (data: TData) => void;

type HandlerFn<TData, TFields extends Record<string, unknown>> = (
  callback: CallbackFn<TData>
) => SseHandler<TFields>;

interface Callbacks {
  [NotificationEvent.ORGANIZATION_CREATED_EVENT]?: CallbackFn<OrganizationCreatedEventDto>;
}
export interface SseHandler<TFields extends Record<string, unknown>> {
  onOrganizationCreatedEvent: HandlerFn<OrganizationCreatedEventDto, TFields>;
  handle: (sse: NotificationEventDto<NotificationEvent>) => void;
}

export const sseHandler = <TFields extends Record<string, any>>(
  callbacks: Callbacks = {}
): SseHandler<TFields> => {
  const handle = (sse: NotificationEventDto<NotificationEvent>): void => {
    const callback = callbacks[sse.event as NotificationEvent];

    if (callback) {
      callback(sse as any);
    }
  };
  return {
    onOrganizationCreatedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ORGANIZATION_CREATED_EVENT]: callback }),
    handle,
  };
};
