import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { acceptInviteMember } from '@organizations/api';

export const useAcceptInviteMember = (): UseMutationResult<
  AxiosResponse<unknown, unknown>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  string,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useMutation((organizationId) => acceptInviteMember(organizationId, authorizationHeaders));
};
