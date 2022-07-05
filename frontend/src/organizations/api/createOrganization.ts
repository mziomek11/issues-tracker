import axios, { AxiosResponse } from 'axios';
import { CreateOrganizationDto, OrganizationCreatedDto } from '@organizations/dtos';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';

export interface CreateOrganizationProps {
  dto: CreateOrganizationDto;
}

export const createOrganization = (
  { dto }: CreateOrganizationProps,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>> => {
  return axios.post('/api/v1/organization-management/organizations', dto, authorizationHeaders);
};
