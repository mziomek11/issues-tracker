import jwt_decode from 'jwt-decode';
import { TimeUnit } from '@shared/enums/time-unit';

interface DecodedJwt {
  sub: string;
  exp: number;
}
export const isTokenExpired = (token: string): boolean => {
  const decodedJwt: DecodedJwt = jwt_decode(token);
  return decodedJwt.exp * TimeUnit.SECOND - TimeUnit.MINUTE > Date.now();
};
