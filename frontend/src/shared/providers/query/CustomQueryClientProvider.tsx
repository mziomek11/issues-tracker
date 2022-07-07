import { useEffect } from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { useUser } from '@users/contexts';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { invalidateOrganizationCreated } from '@shared/helpers/invalidation-cache';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: { refetchOnWindowFocus: false, refetchOnMount: false },
  },
});

interface CustomQueryClientProviderProps {
  children: JSX.Element;
}

export const CustomQueryClientProvider: React.FC<CustomQueryClientProviderProps> = ({
  children,
}) => {
  const handler = sseHandler().onOrganizationCreatedEvent(() =>
    invalidateOrganizationCreated(queryClient)
  );
  useSseSubscription(handler);
  const { jwt } = useUser();
  useEffect(() => {
    if (jwt) return;
    queryClient.clear();
  }, [jwt]);

  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
};
