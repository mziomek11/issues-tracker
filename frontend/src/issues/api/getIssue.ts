import axios, { AxiosResponse } from 'axios';
import { IssuesDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { IssueDetailsDto } from '@issues/dtos';

export const getIssue = (
  params: IssuesDetailsParams,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<IssueDetailsDto, unknown>> =>
  axios.get(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}`,
    authorizationHeaders
  );
