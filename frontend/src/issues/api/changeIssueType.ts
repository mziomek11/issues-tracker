import axios, { AxiosResponse } from 'axios';
import { IssuesDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { ChangeIssueTypeDto } from '@issues/dtos';

export const changeIssueType = (
  params: IssuesDetailsParams,
  dto: ChangeIssueTypeDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, ChangeIssueTypeDto>> =>
  axios.patch(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/type`,
    dto,
    authorizationHeaders
  );
