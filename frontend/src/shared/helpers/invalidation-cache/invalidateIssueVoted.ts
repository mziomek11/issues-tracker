import { QueryClient } from 'react-query';
import { GET_PROJECT_ISSUES, GET_ISSUE } from '@shared/consts/react-query-keys';

export const invalidateIssueVoted = async (
  projectId: string,
  issueId: string,
  queryClient: QueryClient
): Promise<void> => {
  queryClient.invalidateQueries([GET_PROJECT_ISSUES, projectId]);
  queryClient.invalidateQueries([GET_ISSUE, issueId]);
};
