import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { voteIssue } from '@issues/api';
import { VoteIssueDto } from '@issues/dtos';
import { IssuesDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useVoteIssue = (
  params: IssuesDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, VoteIssueDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  VoteIssueDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation((dto: VoteIssueDto) => voteIssue(params, dto, authorizationHeaders));
};
