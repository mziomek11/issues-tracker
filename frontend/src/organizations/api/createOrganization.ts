import axios, { AxiosResponse } from 'axios';
import { CreateOrganizationDto, OrganizationCreatedDto } from '@organizations/dtos';
import { AuthorizationHeadersDto } from '@shared/dtos/api-headers';

export interface CreateOrganizationProps<TParams extends CreateOrganizationDto> {
  dto: TParams;
}

export const createOrganization = (
  { dto }: CreateOrganizationProps<CreateOrganizationDto>,
  authorizationHeaders: AuthorizationHeadersDto
): Promise<AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>> =>
  axios.post('/api/v1/organization-management/organizations', dto, authorizationHeaders);
