import { QueryClient } from 'react-query';
import {
  GET_ORGANIZATION_DETAILS_QUERY,
  GET_INVITATIONS_QUERY,
} from '@shared/consts/react-query-keys';

export const invalidateOrganizationMemberJoined = async (
  organizationId: string,
  queryClient: QueryClient
): Promise<void> => {
  queryClient.invalidateQueries([GET_ORGANIZATION_DETAILS_QUERY, organizationId]);
  queryClient.invalidateQueries(GET_INVITATIONS_QUERY);
};
