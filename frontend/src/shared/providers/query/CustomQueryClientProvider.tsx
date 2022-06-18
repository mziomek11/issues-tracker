import { QueryClient, QueryClientProvider } from 'react-query';

const queryClient = new QueryClient();

interface CustomQueryClientProviderProps {
  children: JSX.Element;
}

export const CustomQueryClientProvider: React.FC<CustomQueryClientProviderProps> = ({
  children,
}) => {
  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
};
