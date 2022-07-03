import { AuthorizationHeadersDto } from '@shared/dtos/api-headers';
import axios from 'axios';

export const getUserOrganizations = (authorizationHeaders: AuthorizationHeadersDto) =>
  axios.get('/api/v1/organization-management/organizations', authorizationHeaders);
