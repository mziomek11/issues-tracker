import { QueryClient } from 'react-query';
import { GET_ORGANIZATION_DETAILS_QUERY } from '@shared/consts/react-query-keys';

export const invalidateOrganizationProjectCreated = (queryClient: QueryClient): Promise<void> =>
  queryClient.invalidateQueries(GET_ORGANIZATION_DETAILS_QUERY, { refetchInactive: true });
