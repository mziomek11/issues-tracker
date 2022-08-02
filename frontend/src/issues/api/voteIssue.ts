import axios, { AxiosResponse } from 'axios';
import { IssuesDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { VoteIssueDto } from '@issues/dtos';

export const voteIssue = (
  params: IssuesDetailsParams,
  dto: VoteIssueDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, VoteIssueDto>> =>
  axios.post(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/votes`,
    dto,
    authorizationHeaders
  );
