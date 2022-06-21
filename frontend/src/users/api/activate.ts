import axios, { AxiosResponse } from 'axios';
import { UserActivationParams } from '@users/types/activation';

export const activate = (dto: UserActivationParams): Promise<AxiosResponse<string, string>> => {
  return axios.post(`/api/v1/user-management/users/${dto.userId}/activation-token`, {
    activationToken: dto.activationToken,
  });
};
