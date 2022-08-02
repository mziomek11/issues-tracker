import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { hideComment } from '@issues/api';
import { IssuesCommentDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useHideComment = (
  params: IssuesCommentDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  unknown,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation(() => hideComment(params, authorizationHeaders));
};
