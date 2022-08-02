import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { commentIssue } from '@issues/api';
import { CommentIssueDto } from '@issues/dtos';
import { IssuesDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { ObjectIdDto } from '@shared/dtos';

export const useCommentIssue = (
  params: IssuesDetailsParams
): UseMutationResult<
  AxiosResponse<ObjectIdDto, CommentIssueDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  CommentIssueDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation((dto: CommentIssueDto) => commentIssue(params, dto, authorizationHeaders));
};
