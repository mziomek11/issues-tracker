import axios, { AxiosResponse } from 'axios';
import { MemberInvitationDto } from '@organizations/dtos/MemberInvitationDto';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';

export const acceptInviteMember = (
  organizationId: string,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, MemberInvitationDto>> =>
  axios.post(
    `/api/v1/organization-management/organizations/${organizationId}/members`,
    null,
    authorizationHeaders
  );
