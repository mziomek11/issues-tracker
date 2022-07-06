import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { activate } from '@users/api';
import { UserActivationParams } from '@users/types/activation';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useActivate = (): UseMutationResult<
  AxiosResponse<string, string>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  UserActivationParams,
  unknown
> => useMutation(activate);
