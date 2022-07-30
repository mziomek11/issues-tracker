import axios, { AxiosResponse } from 'axios';
import { IssuesListParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { CreateIssueDto, IssueCreatedDto } from '@issues/dtos';

export const createIssue = (
  params: IssuesListParams,
  dto: CreateIssueDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<IssueCreatedDto, CreateIssueDto>> =>
  axios.post(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues`,
    dto,
    authorizationHeaders
  );
