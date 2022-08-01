import { QueryClient } from 'react-query';
import { GET_PROJECT_ISSUES } from '@shared/consts/react-query-keys';

export const invalidateIssueCreated = (
  projectId: string,
  queryClient: QueryClient
): Promise<void> =>
  queryClient.invalidateQueries([GET_PROJECT_ISSUES, projectId], { refetchInactive: true });
