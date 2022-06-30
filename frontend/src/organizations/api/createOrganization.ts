import axios, { AxiosResponse } from 'axios';
import { CreateOrganizationDto } from '@organizations/dtos';
import { OrganizationCreatedDto } from '@organizations/components';

export interface CreateOrganizationProps<TParams extends CreateOrganizationDto> {
  dto: TParams;
  jwt: string;
}

export const createOrganization = ({
  dto,
  jwt,
}: CreateOrganizationProps<CreateOrganizationDto>): Promise<
  AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>
> =>
  axios.post('/api/v1/organization-management/organizations', dto, {
    headers: { Authorization: `Bearer ${jwt}` },
  });
