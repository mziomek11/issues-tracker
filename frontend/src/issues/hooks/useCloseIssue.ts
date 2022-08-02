import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { closeIssue } from '@issues/api';
import { IssuesDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useCloseIssue = (
  params: IssuesDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  unknown,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation(() => closeIssue(params, authorizationHeaders));
};
