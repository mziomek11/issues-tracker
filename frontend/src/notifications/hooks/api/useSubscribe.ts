import { useEffect, useRef } from 'react';
import { subscribe } from '@notifications/api/subscribe';
import { NotificationEventDto } from '@notifications/dtos/notification-event';

export const useSubscribe = (
  jwt: string | undefined,
  handleSse: (sse: NotificationEventDto<Record<string, unknown>>) => void
): void => {
  const userAbortController = useRef<AbortController>(null as any);
  useEffect(() => {
    if (jwt) {
      userAbortController.current = subscribe(jwt, (sse) => {
        console.log(sse);
        handleSse(sse);
      });
    } else if (userAbortController.current) {
      userAbortController.current.abort();
    }
  }, [jwt]);
};
