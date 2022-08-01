import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryOptions, UseQueryResult } from 'react-query';
import { getIssue } from '@issues/api';
import { IssuesDetailsParams } from '@issues/types';
import { IssueDetailsDto } from '@issues/dtos';
import { GET_ISSUE } from '@shared/consts/react-query-keys';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useIssue = (
  params: IssuesDetailsParams,
  options?: UseQueryOptions<
    AxiosResponse<IssueDetailsDto, unknown>,
    AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
    AxiosResponse<IssueDetailsDto, unknown>,
    [string, string]
  >
): UseQueryResult<
  AxiosResponse<IssueDetailsDto, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(
    [GET_ISSUE, params.issueId],
    () => getIssue(params, authorizationHeaders),
    options
  );
};
