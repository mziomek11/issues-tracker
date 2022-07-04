import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { register } from '@users/api';
import { RegisterUserDto } from '@users/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useRegister = (): UseMutationResult<
  AxiosResponse<string, RegisterUserDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  RegisterUserDto,
  unknown
> => useMutation(register);
