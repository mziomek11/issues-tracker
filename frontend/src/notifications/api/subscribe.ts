import { EventSourceMessage, fetchEventSource } from '@microsoft/fetch-event-source';

export const subscribe = (jwt: string, handleSse: (sse: any) => void): AbortController => {
  const userAbortController = new AbortController();
  fetchEventSource(`/api/v1/notification-management/notifications/users`, {
    signal: userAbortController.signal,
    headers: {
      Authorization: `Bearer ${jwt}`,
    },
    onmessage: (message: EventSourceMessage) => {
      handleSse({ ...message, data: JSON.parse(message.data) });
    },
    openWhenHidden: true,
  });
  return userAbortController;
};
