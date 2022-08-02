import axios, { AxiosResponse } from 'axios';
import { IssuesDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { CommentIssueDto } from '@issues/dtos';
import { ObjectIdDto } from '@shared/dtos';

export const commentIssue = (
  params: IssuesDetailsParams,
  dto: CommentIssueDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<ObjectIdDto, CommentIssueDto>> =>
  axios.post(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/comments`,
    dto,
    authorizationHeaders
  );
