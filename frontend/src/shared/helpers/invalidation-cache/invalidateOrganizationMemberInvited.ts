import { QueryClient } from 'react-query';
import { GET_INVITATIONS_QUERY } from '@shared/consts/react-query-keys';

export const invalidateOrganizationMemberInvited = async (
  queryClient: QueryClient
): Promise<void> => {
  queryClient.invalidateQueries(GET_INVITATIONS_QUERY);
};
