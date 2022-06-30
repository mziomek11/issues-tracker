import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { createOrganization, CreateOrganizationProps } from '@organizations/api';
import { CreateOrganizationDto } from '@organizations/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { OrganizationCreatedDto } from '@organizations/components';

export const useCreateOrganization = (): UseMutationResult<
  AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>,
  AxiosError<ApplicationErrorDto<any, any>, unknown>,
  CreateOrganizationProps<CreateOrganizationDto>,
  unknown
> => useMutation(createOrganization);
