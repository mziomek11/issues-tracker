import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { createProject, CreateProjectProps } from '@organizations/api';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { CreateProjectDto, ProjectCreatedDto } from '@organizations/dtos';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const useCreateProject = (): UseMutationResult<
  AxiosResponse<ProjectCreatedDto, CreateProjectDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  CreateProjectProps,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useMutation((dto) => createProject(dto, authorizationHeaders));
};
