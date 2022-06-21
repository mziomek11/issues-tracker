import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { login } from '@users/api';
import { LoginDto } from '@users/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';

export const useLogin = (): UseMutationResult<
  AxiosResponse<string, LoginDto>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>,
  LoginDto,
  unknown
> => useMutation(login);
