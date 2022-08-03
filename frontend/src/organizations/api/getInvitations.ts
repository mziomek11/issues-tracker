import axios, { AxiosResponse } from 'axios';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { ListInvitationDto } from '@organizations/dtos';

export const getInvitations = (
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<ListInvitationDto[], unknown>> =>
  axios.get('/api/v1/organization-management/invitations', authorizationHeaders);
