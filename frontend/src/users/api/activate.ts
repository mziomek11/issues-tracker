import { UserActivationParamsDto } from '@users/dtos';
import axios, { AxiosResponse } from 'axios';

export const activate = (dto: UserActivationParamsDto): Promise<AxiosResponse<string, string>> => {
  return axios.post(`/api/v1/user-management/users/${dto.userId}/activation-token`, {
    activationToken: dto.activationToken,
  });
};
