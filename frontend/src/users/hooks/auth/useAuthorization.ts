import jwt_decode from 'jwt-decode';
import { useEffect } from 'react';
import { TimeUnit } from '@shared/enums/time-unit';
import { useJwt } from '@users/contexts';

interface DecodedJwt {
  sub: string;
  exp: number;
}

export const useAuthorization = (): void => {
  const { jwt, removeJwt } = useJwt();
  const isTokenExpired = (token: string): boolean => {
    const decodedJwt: DecodedJwt = jwt_decode(token);
    return decodedJwt.exp * TimeUnit.SECOND - TimeUnit.MINUTE > Date.now();
  };
  useEffect(() => {
    if (!jwt) return;

    if (!isTokenExpired(jwt)) return removeJwt();
  }, []);
};
