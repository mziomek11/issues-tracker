import { AxiosError, AxiosResponse } from 'axios';
import { useQuery, UseQueryResult } from 'react-query';
import { getInvitations } from '@organizations/api';
import { ListInvitationDto } from '@organizations/dtos';
import { GET_INVITATIONS_QUERY } from '@shared/consts/react-query-keys';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useInvitations = (): UseQueryResult<
  AxiosResponse<ListInvitationDto[], unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useQuery(GET_INVITATIONS_QUERY, () => getInvitations(authorizationHeaders));
};
