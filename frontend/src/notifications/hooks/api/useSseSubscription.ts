import { useEffect } from 'react';
import { useSse } from '@notifications/contexts';
import { SseHandler } from '@notifications/helpers/sse-handler';

export const useSseSubscription = (handler: SseHandler<Record<string, any>>): void => {
  const { subscribe, unsubscribe } = useSse();
  useEffect(() => {
    const subscriptionId = subscribe(handler);

    return (): void => unsubscribe(subscriptionId);
  }, [handler]);
};
