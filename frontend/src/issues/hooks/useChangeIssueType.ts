import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { changeIssueType } from '@issues/api';
import { ChangeIssueTypeDto } from '@issues/dtos';
import { IssuesDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useChangeIssueType = (
  params: IssuesDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, ChangeIssueTypeDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  ChangeIssueTypeDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation((dto: ChangeIssueTypeDto) =>
    changeIssueType(params, dto, authorizationHeaders)
  );
};
