import axios, { AxiosResponse } from 'axios';
import { IssuesCommentDetailsParams } from '@issues/types';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { VoteCommentDto } from '@issues/dtos';

export const voteComment = (
  params: IssuesCommentDetailsParams,
  dto: VoteCommentDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, VoteCommentDto>> =>
  axios.post(
    `/api/v1/issue-management/organizations/${params.organizationId}/projects/${params.projectId}/issues/${params.issueId}/comments/${params.commentId}/votes`,
    dto,
    authorizationHeaders
  );
