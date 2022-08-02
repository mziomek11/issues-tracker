import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { changeIssueContent } from '@issues/api';
import { ChangeIssueContentDto } from '@issues/dtos';
import { IssuesDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useChangeIssueContent = (
  params: IssuesDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, ChangeIssueContentDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  ChangeIssueContentDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation((dto: ChangeIssueContentDto) =>
    changeIssueContent(params, dto, authorizationHeaders)
  );
};
