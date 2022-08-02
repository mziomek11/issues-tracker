import jwt_decode from 'jwt-decode';
import { TimeUnit } from '@shared/enums/time-unit';
import { DecodedJWTDto } from '@users/dtos';

export const isTokenExpired = (token: string): boolean => {
  const decodedJwt: DecodedJWTDto = jwt_decode(token);
  return decodedJwt.exp * TimeUnit.SECOND - TimeUnit.MINUTE > Date.now();
};
