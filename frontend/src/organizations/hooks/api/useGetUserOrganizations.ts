import { getUserOrganizations } from '@organizations/api';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { useQuery } from 'react-query';

export const useGetUserOrganizations = () => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery('userOrganizationsList', () => getUserOrganizations(authorizationHeaders));
};
