import axios, { AxiosResponse } from 'axios';
import { IssuesDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { ChangeIssueTypeDto } from '@issues/dtos';

export const closeIssue = (
  params: IssuesDetailsParams,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, ChangeIssueTypeDto>> =>
  axios.delete(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}`,
    authorizationHeaders
  );
