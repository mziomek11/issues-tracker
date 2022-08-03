import { QueryClient } from 'react-query';
import {
  GET_ORGANIZATION_DETAILS_QUERY,
  GET_ORGANIZATIONS_QUERY,
} from '@shared/consts/react-query-keys';

export const invalidateOrganizationProjectCreated = async (
  organizationId: string,
  queryClient: QueryClient
): Promise<void> => {
  queryClient.invalidateQueries(GET_ORGANIZATIONS_QUERY);
  queryClient.invalidateQueries([GET_ORGANIZATION_DETAILS_QUERY, organizationId]);
};
