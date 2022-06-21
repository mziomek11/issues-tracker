import { useMutation, UseMutationResult } from 'react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { activate } from '@users/api';
import { UserActivationParams } from '@users/types/activation';
import { ApplicationErrorDto } from '@shared/dtos/application-error';

export const useActivate = (): UseMutationResult<
  AxiosResponse<string, string>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>,
  UserActivationParams,
  unknown
> => useMutation(activate);
