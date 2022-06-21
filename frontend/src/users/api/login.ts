import { LoginDto } from '@users/dtos';
import axios, { AxiosResponse } from 'axios';

export const login = (dto: LoginDto): Promise<AxiosResponse<any, any>> =>
  axios.post('/api/v1/user-management/users/authentication', dto);
