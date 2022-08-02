import axios, { AxiosResponse } from 'axios';
import { IssuesCommentDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { ChangeCommentContentDto } from '@issues/dtos';

export const changeCommentContent = (
  params: IssuesCommentDetailsParams,
  dto: ChangeCommentContentDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, ChangeCommentContentDto>> =>
  axios.patch(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/comments/${params.commentId}/content`,
    dto,
    authorizationHeaders
  );
