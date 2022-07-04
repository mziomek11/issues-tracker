import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { createOrganization, CreateOrganizationProps } from '@organizations/api';
import { CreateOrganizationDto, OrganizationCreatedDto } from '@organizations/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useCreateOrganization = (): UseMutationResult<
  AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  CreateOrganizationProps<CreateOrganizationDto>,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useMutation((dto) => createOrganization(dto, authorizationHeaders));
};
