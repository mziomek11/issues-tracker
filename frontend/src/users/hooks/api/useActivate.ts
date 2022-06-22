import { useMutation, UseMutationResult } from 'react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { activate } from '@users/api';
import { UserActivationParams } from '@users/types/activation';
import { ApplicationErrorResponseDto } from '@shared/dtos/application-error';

export const useActivate = (): UseMutationResult<
  AxiosResponse<string, string>,
  AxiosError<ApplicationErrorResponseDto<any, any>, unknown>,
  UserActivationParams,
  unknown
> => useMutation(activate);
