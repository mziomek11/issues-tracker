import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { voteComment } from '@issues/api';
import { VoteCommentDto } from '@issues/dtos';
import { IssuesCommentDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useVoteComment = (
  params: IssuesCommentDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, VoteCommentDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  VoteCommentDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation((dto: VoteCommentDto) => voteComment(params, dto, authorizationHeaders));
};
