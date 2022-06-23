import axios, { AxiosResponse } from 'axios';
import { UserActivationParams } from '@users/types/activation';

export const activate = ({
  userId,
  activationToken,
}: UserActivationParams): Promise<AxiosResponse<string, string>> =>
  axios.post(`/api/v1/user-management/users/${userId}/activation-token`, {
    activationToken,
  });
