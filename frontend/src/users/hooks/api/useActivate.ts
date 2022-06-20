import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { activate } from '@users/api';
import { UserActivationParamsDto } from '@users/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';

export const useActivate = (): UseMutationResult<
  AxiosResponse<string, string>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>,
  UserActivationParamsDto,
  unknown
> => useMutation(activate);
