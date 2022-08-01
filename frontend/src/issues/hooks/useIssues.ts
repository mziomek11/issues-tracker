import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryResult } from 'react-query';
import { getIssues } from '@issues/api';
import { GET_PROJECT_ISSUES } from '@issues/consts/react-query-keys';
import { IssuesListParams } from '@issues/types';
import { IssuesListDto } from '@issues/dtos';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

interface UseQueryConfig {
  onError?: (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => void;
  onSuccess?: (response: AxiosResponse<IssuesListDto, unknown>) => void;
  onSettled?: (data?: any, error?: any) => void;
}

export const useIssues = (
  params: IssuesListParams,
  useQueryConfig?: UseQueryConfig
): UseQueryResult<
  AxiosResponse<IssuesListDto, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(
    [GET_PROJECT_ISSUES, params.projectId],
    () => getIssues(params, authorizationHeaders),
    useQueryConfig
  );
};
