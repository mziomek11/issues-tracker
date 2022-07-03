import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { createOrganization, CreateOrganizationProps } from '@organizations/api';
import { CreateOrganizationDto, OrganizationCreatedDto } from '@organizations/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useCreateOrganization = (): UseMutationResult<
  AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>,
  CreateOrganizationProps<CreateOrganizationDto>,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useMutation((dto) => createOrganization(dto, authorizationHeaders));
};
