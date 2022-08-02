import axios, { AxiosResponse } from 'axios';
import { IssuesDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { ChangeIssueContentDto } from '@issues/dtos';

export const changeIssueContent = (
  params: IssuesDetailsParams,
  dto: ChangeIssueContentDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, ChangeIssueContentDto>> =>
  axios.patch(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/content`,
    dto,
    authorizationHeaders
  );
