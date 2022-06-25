import axios, { AxiosResponse } from 'axios';
import { LoginDto } from '@users/dtos';

export const login = (dto: LoginDto): Promise<AxiosResponse<any, any>> =>
  axios.post('/api/v1/user-management/users/authentication', dto);
