import axios, { AxiosResponse } from 'axios';
import { IssuesListParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { IssuesListDto } from '@issues/dtos';

export const getIssues = (
  params: IssuesListParams,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<IssuesListDto, unknown>> =>
  axios.get(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues`,
    authorizationHeaders
  );
