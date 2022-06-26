import { useEffect } from 'react';
import { subscribe } from '@users/api/subscribe';

export const useSubscribe = (jwt: string | undefined) => {
  useEffect(() => {
    if (jwt) subscribe(jwt);
  }, [jwt]);
};
