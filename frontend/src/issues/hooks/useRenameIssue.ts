import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { renameIssue } from '@issues/api';
import { RenameIssueDto } from '@issues/dtos';
import { IssuesDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useRenameIssue = (
  params: IssuesDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, RenameIssueDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  RenameIssueDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation((dto: RenameIssueDto) => renameIssue(params, dto, authorizationHeaders));
};
