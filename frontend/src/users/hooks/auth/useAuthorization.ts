import jwt_decode from 'jwt-decode';
import { TimeUnit } from '@shared/enums/time-unit';
import { useEffect } from 'react';
import { JWT } from '@users/consts/localstorage';

interface DecodedJwt {
  sub: string;
  exp: number;
}

export const useAuthorization = (): void => {
  const isTokenExpired = (token: string): boolean => {
    const decodedJwt: DecodedJwt = jwt_decode(token);
    return decodedJwt.exp * TimeUnit.SECOND - TimeUnit.MINUTE > Date.now();
  };
  useEffect(() => {
    const currrentJwt = localStorage.getItem(JWT);
    if (!currrentJwt) return;

    if (!isTokenExpired(currrentJwt)) return localStorage.removeItem(JWT);
  }, []);
};
