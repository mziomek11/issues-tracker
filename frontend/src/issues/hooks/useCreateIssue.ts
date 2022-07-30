import { createIssue } from '@issues/api';
import { CreateIssueDto, IssueCreatedDto } from '@issues/dtos';
import { IssuesListParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';
import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';

interface UseQueryConfig {
  onError?: (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => void;
  onSuccess?: (response: AxiosResponse<IssueCreatedDto, CreateIssueDto>) => void;
  onSettled?: (data?: any, error?: any) => void;
}

export const useCreateIssue = (
  params: IssuesListParams,
  queryConfig: UseQueryConfig
): UseMutationResult<
  AxiosResponse<IssueCreatedDto, CreateIssueDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  CreateIssueDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();
  return useMutation(
    (dto: CreateIssueDto) => createIssue(params, dto, authorizationHeaders),
    queryConfig
  );
};
