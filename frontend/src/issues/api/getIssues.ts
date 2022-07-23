import axios from 'axios';
import { IssuesListParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';

export const getIssues = (params: IssuesListParams, authorizationHeaders: AuthorizationHeaders) =>
  axios.get(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues`,
    authorizationHeaders
  );
