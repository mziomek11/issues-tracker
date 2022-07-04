import axios from 'axios';
import { CreateProjectDto } from '@organizations/dtos';
import { AuthorizationHeadersDto } from '@shared/dtos/api-headers';

export interface CreateProjectProps<TParams extends CreateProjectDto> {
  dto: TParams;
  organizationId: string;
}

export const createProject = (
  { dto, organizationId }: CreateProjectProps<CreateProjectDto>,
  authorizationHeaders: AuthorizationHeadersDto
) =>
  axios.post(
    `/api/v1/organization-management/organizations/${organizationId}/projects`,
    dto,
    authorizationHeaders
  );
