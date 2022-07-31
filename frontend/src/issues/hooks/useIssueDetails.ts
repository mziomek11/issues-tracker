import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryResult } from 'react-query';
import { IssueDetailsDto } from '@issues/dtos';
import { getIssueDetails } from '@issues/api';
import { IssueDetailsParams } from '@issues/types';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { GET_ISSUE_DETAILS_QUERY } from '@shared/consts/react-query-keys';

interface UseQueryConfig {
  onError?: (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => void;
  onSuccess?: (response: AxiosResponse<IssueDetailsDto, unknown>) => void;
}

export const useIssueDetails = (
  params: IssueDetailsParams,
  useQueryConfig?: UseQueryConfig
): UseQueryResult<
  AxiosResponse<IssueDetailsDto, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(
    [GET_ISSUE_DETAILS_QUERY, params.issueId],
    () => getIssueDetails(params, authorizationHeaders),
    useQueryConfig
  );
};
