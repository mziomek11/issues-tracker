import axios, { AxiosResponse } from 'axios';
import { CreateOrganizationDto } from 'organizations/dtos';
// import { CreateOrganizationDto } from 'organizations/dtos';
interface CreateOrganizationProps<TParams extends CreateOrganizationDto> {
  dto: TParams;
  jwt: string;
}

export const createOrganization = ({
  dto,
  jwt,
}: CreateOrganizationProps<CreateOrganizationDto>): Promise<AxiosResponse<any, any>> =>
  axios.post('/api/v1/organization-management/organizations', dto, {
    headers: { Authorization: `Bearer ${jwt}` },
  });
