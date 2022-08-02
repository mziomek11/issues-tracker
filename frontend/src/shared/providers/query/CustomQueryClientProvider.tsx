import { useEffect } from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { useUser } from '@users/contexts';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import {
  invalidateIssueClosed,
  invalidateIssueContentChanged,
  invalidateIssueCreated,
  invalidateIssueRenamed,
  invalidateIssueTypeChanged,
  invalidateIssueVoted,
  invalidateOrganizationCreated,
  invalidateOrganizationProjectCreated,
} from '@shared/helpers/invalidation-cache';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: { refetchOnWindowFocus: false, staleTime: Infinity },
  },
});

interface CustomQueryClientProviderProps {
  children: JSX.Element;
}

export const CustomQueryClientProvider: React.FC<CustomQueryClientProviderProps> = ({
  children,
}) => {
  const handler = sseHandler()
    .onIssueClosedEvent(({ data }) =>
      invalidateIssueClosed(data.projectId, data.issueId, queryClient)
    )
    .onIssueContentChangedEvent(({ data }) =>
      invalidateIssueContentChanged(data.projectId, data.issueId, queryClient)
    )
    .onIssueOpenedEvent(({ data }) => invalidateIssueCreated(data.projectId, queryClient))
    .onIssueRenamedEvent(({ data }) =>
      invalidateIssueRenamed(data.projectId, data.issueId, queryClient)
    )
    .onIssueTypeChangedEvent(({ data }) =>
      invalidateIssueTypeChanged(data.projectId, data.issueId, queryClient)
    )
    .onIssueVotedEvent(({ data }) =>
      invalidateIssueVoted(data.projectId, data.issueId, queryClient)
    )
    .onOrganizationCreatedEvent(() => invalidateOrganizationCreated(queryClient))
    .onOrganizationProjectCreatedEvent(() => invalidateOrganizationProjectCreated(queryClient));

  useSseSubscription(handler);
  const { jwt } = useUser();
  useEffect(() => {
    if (jwt) return;
    queryClient.clear();
  }, [jwt]);

  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
};
