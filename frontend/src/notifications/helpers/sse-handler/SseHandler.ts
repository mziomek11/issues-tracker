import {
  IssueOpenedEventDto,
  NotificationEventDto,
  OrganizationCreatedEventDto,
  OrganizationMemberInvitedEventDto,
  OrganizationMemberJoinedEventDto,
  OrganizationProjectCreatedEventDto,
} from '@notifications/dtos/notification-event';
import { NotificationEvent } from '@notifications/enums/notification-event';

type CallbackFn<TData> = (data: TData) => void;

type HandlerFn<TData> = (callback: CallbackFn<TData>) => SseHandler;

interface Callbacks {
  [NotificationEvent.ORGANIZATION_CREATED]?: CallbackFn<OrganizationCreatedEventDto>;
  [NotificationEvent.ORGANIZATION_PROJECT_CREATED]?: CallbackFn<OrganizationProjectCreatedEventDto>;
  [NotificationEvent.ORGANIZATION_MEMBER_INVITED]?: CallbackFn<OrganizationMemberInvitedEventDto>;
  [NotificationEvent.ORGANIZATION_MEMBER_JOINED]?: CallbackFn<OrganizationMemberJoinedEventDto>;
  [NotificationEvent.ISSUE_OPENED]?: CallbackFn<IssueOpenedEventDto>;
}
export interface SseHandler {
  onOrganizationCreatedEvent: HandlerFn<OrganizationCreatedEventDto>;
  onOrganizationProjectCreatedEvent: HandlerFn<OrganizationProjectCreatedEventDto>;
  onOrganizationMemberInvitedEvent: HandlerFn<OrganizationMemberInvitedEventDto>;
  onOrganizationMemberJoinedEvent: HandlerFn<OrganizationMemberJoinedEventDto>;
  onIssueOpenedEvent: HandlerFn<IssueOpenedEventDto>;
  handle: (sse: NotificationEventDto<Record<string, unknown>>) => void;
}

export const sseHandler = (callbacks: Callbacks = {}): SseHandler => {
  const handle = (sse: NotificationEventDto<Record<string, unknown>>): void => {
    const callback = callbacks[sse.event as NotificationEvent];

    if (callback) {
      callback(sse as any);
    }
  };
  return {
    onOrganizationCreatedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ORGANIZATION_CREATED]: callback }),
    onOrganizationProjectCreatedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ORGANIZATION_PROJECT_CREATED]: callback }),
    onOrganizationMemberInvitedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ORGANIZATION_MEMBER_INVITED]: callback }),
    onOrganizationMemberJoinedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ORGANIZATION_MEMBER_JOINED]: callback }),
    onIssueOpenedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_OPENED]: callback }),
    handle,
  };
};
