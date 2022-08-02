import {
  IssueClosedEventDto,
  IssueCommentContentChangedEventDto,
  IssueCommentedEventDto,
  IssueCommentVotedEventDto,
  IssueContentChangedEventDto,
  IssueOpenedEventDto,
  IssueRenamedEventDto,
  IssueTypeChangedEventDto,
  IssueVotedEventDto,
  NotificationEventDto,
  OrganizationCreatedEventDto,
  OrganizationMemberInvitedEventDto,
  OrganizationMemberJoinedEventDto,
  OrganizationProjectCreatedEventDto,
} from '@notifications/dtos/notification-event';
import { NotificationEvent } from '@notifications/enums/notification-event';
import { IssueCommentHiddenErrorDto } from '@shared/dtos/application-error';

type CallbackFn<TData> = (data: TData) => void;

type HandlerFn<TData> = (callback: CallbackFn<TData>) => SseHandler;

interface Callbacks {
  [NotificationEvent.ORGANIZATION_CREATED]?: CallbackFn<OrganizationCreatedEventDto>;
  [NotificationEvent.ORGANIZATION_PROJECT_CREATED]?: CallbackFn<OrganizationProjectCreatedEventDto>;
  [NotificationEvent.ORGANIZATION_MEMBER_INVITED]?: CallbackFn<OrganizationMemberInvitedEventDto>;
  [NotificationEvent.ORGANIZATION_MEMBER_JOINED]?: CallbackFn<OrganizationMemberJoinedEventDto>;
  [NotificationEvent.ISSUE_CLOSED]?: CallbackFn<IssueClosedEventDto>;
  [NotificationEvent.ISSUE_COMMENT_CONTENT_CHANGED]?: CallbackFn<IssueCommentContentChangedEventDto>;
  [NotificationEvent.ISSUE_COMMENTED]?: CallbackFn<IssueCommentedEventDto>;
  [NotificationEvent.ISSUE_COMMENT_HIDDEN]?: CallbackFn<IssueCommentHiddenErrorDto>;
  [NotificationEvent.ISSUE_COMMENT_VOTED]?: CallbackFn<IssueCommentVotedEventDto>;
  [NotificationEvent.ISSUE_CONTENT_CHANGED]?: CallbackFn<IssueContentChangedEventDto>;
  [NotificationEvent.ISSUE_OPENED]?: CallbackFn<IssueOpenedEventDto>;
  [NotificationEvent.ISSUE_RENAMED]?: CallbackFn<IssueRenamedEventDto>;
  [NotificationEvent.ISSUE_TYPE_CHANGED]?: CallbackFn<IssueTypeChangedEventDto>;
  [NotificationEvent.ISSUE_VOTED]?: CallbackFn<IssueVotedEventDto>;
}
export interface SseHandler {
  onOrganizationCreatedEvent: HandlerFn<OrganizationCreatedEventDto>;
  onOrganizationProjectCreatedEvent: HandlerFn<OrganizationProjectCreatedEventDto>;
  onOrganizationMemberInvitedEvent: HandlerFn<OrganizationMemberInvitedEventDto>;
  onOrganizationMemberJoinedEvent: HandlerFn<OrganizationMemberJoinedEventDto>;
  onIssueClosedEvent: HandlerFn<IssueClosedEventDto>;
  onIssueCommentContentChangedEvent: HandlerFn<IssueCommentContentChangedEventDto>;
  onIssueCommentedEvent: HandlerFn<IssueCommentedEventDto>;
  onIssueCommentHiddenEvent: HandlerFn<IssueCommentHiddenErrorDto>;
  onIssueCommentVotedEvent: HandlerFn<IssueCommentVotedEventDto>;
  onIssueContentChangedEvent: HandlerFn<IssueContentChangedEventDto>;
  onIssueOpenedEvent: HandlerFn<IssueOpenedEventDto>;
  onIssueRenamedEvent: HandlerFn<IssueRenamedEventDto>;
  onIssueTypeChangedEvent: HandlerFn<IssueTypeChangedEventDto>;
  onIssueVotedEvent: HandlerFn<IssueVotedEventDto>;
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
    onIssueClosedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_CLOSED]: callback }),
    onIssueCommentContentChangedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_COMMENTED]: callback }),
    onIssueCommentedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_COMMENT_CONTENT_CHANGED]: callback }),
    onIssueCommentHiddenEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_COMMENT_HIDDEN]: callback }),
    onIssueCommentVotedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_COMMENT_VOTED]: callback }),
    onIssueContentChangedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_CONTENT_CHANGED]: callback }),
    onIssueOpenedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_OPENED]: callback }),
    onIssueRenamedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_RENAMED]: callback }),
    onIssueTypeChangedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_TYPE_CHANGED]: callback }),
    onIssueVotedEvent: (callback) =>
      sseHandler({ ...callbacks, [NotificationEvent.ISSUE_VOTED]: callback }),
    handle,
  };
};
