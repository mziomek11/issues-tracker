import axios, { AxiosResponse } from 'axios';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { IssueDetailsParams } from '@issues/types';
import { IssueDetailsDto } from '@issues/dtos';

export const getIssueDetails = (
  params: IssueDetailsParams,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<IssueDetailsDto, unknown>> =>
  axios.get(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}`,
    authorizationHeaders
  );
