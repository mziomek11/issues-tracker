import { fetchEventSource } from '@microsoft/fetch-event-source';

export const subscribe = (jwt: string): AbortController => {
  const userAbortController = new AbortController();
  fetchEventSource(`/api/v1/notification-management/notifications/users`, {
    signal: userAbortController.signal,
    headers: {
      Authorization: `Bearer ${jwt}`,
    },
    onmessage: (message: any) => {
      console.log(JSON.parse(message));
    },
    openWhenHidden: true,
  });
  return userAbortController;
};
