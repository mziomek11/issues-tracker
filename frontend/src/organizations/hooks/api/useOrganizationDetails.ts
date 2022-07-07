import { getOrganizationDetails } from '@organizations/api';
import { GET_ORGANIZATION_DETAILS_QUERY } from '@organizations/consts/react-query-keys';
import { UserOrganizationDto } from '@organizations/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryResult } from 'react-query';

export const useOrganizationDetails = (
  organizationId: string,
  onError: (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => void
): UseQueryResult<
  AxiosResponse<UserOrganizationDto, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(
    [GET_ORGANIZATION_DETAILS_QUERY, organizationId],
    () => getOrganizationDetails(authorizationHeaders, organizationId),
    {
      onError,
    }
  );
};
