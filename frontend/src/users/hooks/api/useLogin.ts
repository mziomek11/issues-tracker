import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { login } from '@users/api';
import { LoginDto } from '@users/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useLogin = (): UseMutationResult<
  AxiosResponse<string, LoginDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  LoginDto,
  unknown
> => useMutation(login);
