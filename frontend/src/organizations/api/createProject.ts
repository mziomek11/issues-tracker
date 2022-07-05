import axios, { AxiosResponse } from 'axios';
import { CreateProjectDto, ProjectCreatedDto } from '@organizations/dtos';
import { AuthorizationHeaders } from '@shared/interfaces/api-headers';

export interface CreateProjectProps {
  dto: CreateProjectDto;
  organizationId: string;
}

export const createProject = (
  { dto, organizationId }: CreateProjectProps,
  authorizationHeaders: AuthorizationHeaders
): Promise<AxiosResponse<ProjectCreatedDto, CreateProjectDto>> =>
  axios.post(
    `/api/v1/organization-management/organizations/${organizationId}/projects`,
    dto,
    authorizationHeaders
  );
