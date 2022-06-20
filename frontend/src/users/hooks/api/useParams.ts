import { useParams as useReactRouterParams } from 'react-router-dom';

export const useParams = <TParams extends Record<string, string>>(): TParams => {
  const params = useReactRouterParams();
  return params as TParams;
};
