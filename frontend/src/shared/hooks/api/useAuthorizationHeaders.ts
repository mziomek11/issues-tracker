import { AuthorizationHeaders } from '@shared/interfaces/api-headers';
import { useUser } from '@users/contexts';

export const useAuthorizationHeaders = (): AuthorizationHeaders => {
  const { jwt } = useUser();
  return {
    headers: { Authorization: `Bearer ${jwt}` },
  };
};
