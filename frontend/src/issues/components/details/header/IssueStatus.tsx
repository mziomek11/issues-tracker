import { useState } from 'react';
import { Text, Button, useToast, Box } from '@chakra-ui/react';
import { IssueDetailsDto } from '@issues/dtos';
import { IssueStatus as IssueStatusEnum } from '@issues/enums';
import { IssuesDetailsParams } from '@issues/types';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { AxiosError } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { useUser } from '@users/contexts';
import { useCloseIssue } from '@issues/hooks';

export interface IssueStatusProps {
  params: IssuesDetailsParams;
  issue: IssueDetailsDto;
}

export const IssueStatus: React.FC<IssueStatusProps> = ({ params, issue }) => {
  const toast = useToast();
  const { userId } = useUser();
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: closeIssue, isLoading } = useCloseIssue(params);
  useSseSubscription(handler);

  const handleCloseClick = (): void => {
    closeIssue(null, {
      onError: handleError,
      onSuccess: handleSuccess,
    });
  };

  const handleSuccess = (): void => {
    setIsWaitingForSse(true);

    setHandler(
      handler.onIssueClosedEvent(({ data }) => {
        if (data.issueId === params.issueId && data.memberId === userId) {
          setIsWaitingForSse(false);
        }
      })
    );
  };

  const handleError = (
    e: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void =>
    applicationErrorHandler<Record<string, string[]>>()
      .onDefault(({ message }) => toast({ status: 'error', title: message }))
      .handleAxiosError(e);

  return (
    <Box>
      {issue.status === IssueStatusEnum.OPENED && (
        <Button onClick={handleCloseClick} isLoading={isLoading || isWaitingForSse}>
          Close
        </Button>
      )}

      {issue.status === IssueStatusEnum.CLOSED && <Text>Issue is closed</Text>}
    </Box>
  );
};
