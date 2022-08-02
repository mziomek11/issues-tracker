import axios, { AxiosResponse } from 'axios';
import { IssuesDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { RenameIssueDto } from '@issues/dtos';

export const renameIssue = (
  params: IssuesDetailsParams,
  dto: RenameIssueDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, RenameIssueDto>> =>
  axios.patch(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/name`,
    dto,
    authorizationHeaders
  );
