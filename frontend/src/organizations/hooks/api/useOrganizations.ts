import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryResult } from 'react-query';
import { getUserOrganizations } from '@organizations/api';
import { UserOrganizationDto } from '@organizations/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { userOrganizationsList } from '@organizations/consts/react-query-keys';

export const useOrganizations = (
  onError: (error: AxiosError<ApplicationErrorDto<any, any>, unknown>) => void
): UseQueryResult<
  AxiosResponse<UserOrganizationDto[], unknown>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(userOrganizationsList, () => getUserOrganizations(authorizationHeaders), {
    onError,
  });
};
