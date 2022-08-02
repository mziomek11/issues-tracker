import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { changeCommentContent } from '@issues/api';
import { ChangeCommentContentDto } from '@issues/dtos';
import { IssuesCommentDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { useAuthorizationHeaders } from '@shared/hooks/api';

export const useChangeCommentContent = (
  params: IssuesCommentDetailsParams
): UseMutationResult<
  AxiosResponse<unknown, ChangeCommentContentDto>,
  AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>,
  ChangeCommentContentDto,
  unknown
> => {
  const authorizationHeaders = useAuthorizationHeaders();

  return useMutation((dto: ChangeCommentContentDto) =>
    changeCommentContent(params, dto, authorizationHeaders)
  );
};
