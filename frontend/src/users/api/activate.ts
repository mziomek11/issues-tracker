import { ActivateUserDto, ActivationTokenDto } from '@users/dtos';
import axios, { AxiosResponse } from 'axios';

export const activate = (
  dto: ActivateUserDto<ActivationTokenDto>
): Promise<AxiosResponse<string, ActivationTokenDto>> => {
  return axios.post(
    `/api/v1/user-management/users/${dto.userId}/activation-token`,
    dto.activationToken
  );
};
