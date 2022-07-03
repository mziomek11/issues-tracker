import { AuthorizationHeadersDto } from '@shared/dtos/api-headers';
import { useUser } from '@users/contexts';

export const useAuthorizationHeaders = (): AuthorizationHeadersDto => {
  const { jwt } = useUser();
  return {
    headers: { Authorization: `Bearer ${jwt}` },
  };
};
