import axios, { AxiosResponse } from 'axios';
import { RegisterUserDto } from 'users/dtos/register-user.dto';

export const register = (
  data: RegisterUserDto
): Promise<AxiosResponse<string, RegisterUserDto>> => {
  return axios.post(`http://localhost/api/v1/user-management/users`, data);
};
