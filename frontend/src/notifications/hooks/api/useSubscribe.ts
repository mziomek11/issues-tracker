import { useEffect, useRef } from 'react';
import { subscribe } from '@notifications/api/subscribe';

export const useSubscribe = (jwt: string | undefined, handleSse: (sse: any) => void): void => {
  const userAbortController = useRef<AbortController>(null as any);
  useEffect(() => {
    if (jwt) {
      userAbortController.current = subscribe(jwt, handleSse);
    } else if (userAbortController.current) {
      userAbortController.current.abort();
    }
  }, [jwt]);
};
