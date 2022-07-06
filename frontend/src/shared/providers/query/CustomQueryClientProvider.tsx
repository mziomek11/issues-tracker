import { useEffect, useState } from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { useUser } from '@users/contexts';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { invalidateOrganizationCreated } from '@shared/helpers/invalidation-cache';

const queryClient = new QueryClient();

interface CustomQueryClientProviderProps {
  children: JSX.Element;
}

export const CustomQueryClientProvider: React.FC<CustomQueryClientProviderProps> = ({
  children,
}) => {
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);
  const { jwt } = useUser();
  useEffect(() => {
    setHandler(
      handler.onOrganizationCreatedEvent(() => invalidateOrganizationCreated(queryClient))
    );
  }, []);
  useEffect(() => {
    if (jwt) return;
    queryClient.clear();
  }, [jwt]);

  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
};
