import axios, { AxiosResponse } from 'axios';
import { IssuesCommentDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';

export const hideComment = (
  params: IssuesCommentDetailsParams,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, unknown>> =>
  axios.delete(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/comments/${params.commentId}`,
    authorizationHeaders
  );
