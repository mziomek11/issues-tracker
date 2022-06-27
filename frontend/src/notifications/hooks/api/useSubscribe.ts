import { useEffect, useRef } from 'react';
import { subscribe } from '@notifications/api/subscribe';

export const useSubscribe = (jwt: string | undefined): void => {
  const userAbortController = useRef<AbortController>(null as any);
  useEffect(() => {
    if (jwt) {
      userAbortController.current = subscribe(jwt);
    } else if (userAbortController.current) {
      userAbortController.current.abort();
    }
  }, [jwt]);
};
