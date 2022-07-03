import axios from 'axios';
import { AuthorizationHeadersDto } from '@shared/dtos/api-headers';

export const getUserOrganizations = (authorizationHeaders: AuthorizationHeadersDto) =>
  axios.get('/api/v1/organization-management/organizations', authorizationHeaders);
