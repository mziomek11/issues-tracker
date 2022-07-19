import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { inviteMember } from '@organizations/api';
import { MemberInvitationDto } from '@organizations/dtos/MemberInvitationDto';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useInviteMember = (
  organizationId: string
): UseMutationResult<
  AxiosResponse<unknown, MemberInvitationDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  MemberInvitationDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useMutation((dto) => inviteMember(organizationId, dto, authorizationHeaders));
};
