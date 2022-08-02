import { QueryClient } from 'react-query';
import { GET_ORGANIZATIONS_QUERY } from '@shared/consts/react-query-keys';

export const invalidateOrganizationCreated = (queryClient: QueryClient): Promise<void> =>
  queryClient.invalidateQueries(GET_ORGANIZATIONS_QUERY);
