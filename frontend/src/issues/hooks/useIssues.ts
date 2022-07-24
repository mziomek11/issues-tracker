import { useQuery } from 'react-query';
import { getIssues } from '@issues/api';
import { GET_PROJECT_ISSUES } from '@issues/consts/react-query-keys';
import { IssuesListParams } from '@issues/types';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { AxiosError, AxiosResponse } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

interface UseQueryConfig {
  onError?: (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => void;
  onSuccess?: (response: AxiosResponse<unknown, unknown>) => void;
  onSettled?: (data?: any, error?: any) => void;
}

export const useIssues = (params: IssuesListParams, useQueryConfig?: UseQueryConfig) => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(
    [GET_PROJECT_ISSUES, params.projectId],
    () => getIssues(params, authorizationHeaders),
    useQueryConfig
  );
};
