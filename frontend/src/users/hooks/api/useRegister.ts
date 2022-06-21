import { useMutation, UseMutationResult } from 'react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { register } from '@users/api';
import { RegisterUserDto } from '@users/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';

export const useRegister = (): UseMutationResult<
  AxiosResponse<string, RegisterUserDto>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>,
  RegisterUserDto,
  unknown
> => useMutation(register);
