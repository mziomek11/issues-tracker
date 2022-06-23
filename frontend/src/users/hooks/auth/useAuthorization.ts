import jwt_decode from 'jwt-decode';
import { AuthorizationConsts } from '@users/enums/authorization-consts';
import { TimeUnit } from '@shared/enums/time-unit';
import { useEffect } from 'react';

interface DecodedJwt {
  sub: string;
  exp: number;
}

export const useAuthorization = (): void => {
  const isTokenExpired = (token: string): boolean => {
    const decodedJwt: DecodedJwt = jwt_decode(token);
    console.log(decodedJwt.exp * TimeUnit.SECOND - TimeUnit.MINUTE, Date.now());
    return decodedJwt.exp * TimeUnit.SECOND - TimeUnit.MINUTE > Date.now();
  };
  useEffect(() => {
    const currrentJwt = localStorage.getItem(AuthorizationConsts.JWT);
    if (!currrentJwt) return;

    if (!isTokenExpired(currrentJwt)) return localStorage.removeItem(AuthorizationConsts.JWT);
  }, []);
};
