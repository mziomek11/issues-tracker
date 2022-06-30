import { useEffect, useRef } from 'react';
import { subscribe } from '@notifications/api/subscribe';
import { NotificationEventDto } from '@notifications/dtos/notification-event';
import { NotificationEvent } from '@notifications/enums/notification-event';

export const useSubscribe = (
  jwt: string | undefined,
  handleSse: (sse: NotificationEventDto<NotificationEvent>) => void
): void => {
  const userAbortController = useRef<AbortController>(null as any);
  useEffect(() => {
    if (jwt) {
      userAbortController.current = subscribe(jwt, handleSse);
    } else if (userAbortController.current) {
      userAbortController.current.abort();
    }
  }, [jwt]);
};
