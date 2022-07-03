import axios, { AxiosResponse } from 'axios';
import { AuthorizationHeadersDto } from '@shared/dtos/api-headers';
import { UserOrganizationDto } from '@organizations/dtos';

export const getUserOrganizations = (
  authorizationHeaders: AuthorizationHeadersDto
): Promise<AxiosResponse<UserOrganizationDto[], unknown>> =>
  axios.get('/api/v1/organization-management/organizations', authorizationHeaders);
