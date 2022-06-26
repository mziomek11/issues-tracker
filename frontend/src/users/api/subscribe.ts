import { fetchEventSource } from '@microsoft/fetch-event-source';

export const userAbortController = new AbortController();

export const subscribe = (jwt: string) => {
  return fetchEventSource(`/api/v1/notification-management/notifications/users`, {
    signal: userAbortController.signal,
    headers: {
      Authorization: `Bearer ${jwt}`,
    },
    onmessage: (message: any) => {
      console.log(JSON.parse(message));
    },
    openWhenHidden: true,
  });
};
