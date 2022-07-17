import axios, { AxiosResponse } from 'axios';
import { MemberInvitationDto } from '@organizations/dtos/MemberInvitationDto';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';

export const inviteMember = (
  organizationId: string,
  dto: MemberInvitationDto,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<unknown, MemberInvitationDto>> =>
  axios.post(
    `/api/v1/organization-management/organizations/${organizationId}/invitations`,
    dto,
    authorizationHeaders
  );
