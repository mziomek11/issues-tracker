import { QueryClient } from 'react-query';
import { GET_ISSUE } from '@shared/consts/react-query-keys';

export const invalidateIssueCommented = async (
  issueId: string,
  queryClient: QueryClient
): Promise<void> => {
  queryClient.invalidateQueries([GET_ISSUE, issueId]);
};
