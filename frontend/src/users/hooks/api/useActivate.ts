import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { activate } from '@users/api';
import { ActivateUserDto, ActivationTokenDto } from '@users/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';

export const useActivate = (): UseMutationResult<
  AxiosResponse<string, ActivationTokenDto>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>,
  ActivateUserDto<ActivationTokenDto>,
  unknown
> => useMutation(activate);
