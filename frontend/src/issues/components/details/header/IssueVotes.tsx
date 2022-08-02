import { useState } from 'react';
import { Text, Button, useToast, Flex } from '@chakra-ui/react';
import { IssueDetailsDto } from '@issues/dtos';
import { IssueStatus, VoteType } from '@issues/enums';
import { IssuesDetailsParams } from '@issues/types';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { AxiosError } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { useUser } from '@users/contexts';
import { useVoteIssue } from '@issues/hooks';

export interface IssueVotesProps {
  params: IssuesDetailsParams;
  issue: IssueDetailsDto;
}

export const IssueVotes: React.FC<IssueVotesProps> = ({ params, issue }) => {
  const toast = useToast();
  const { userId } = useUser();
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: voteIssue, isLoading } = useVoteIssue(params);
  useSseSubscription(handler);

  const canVote = (voteType: VoteType): boolean =>
    !isLoading &&
    !isWaitingForSse &&
    !issue.votes.some((vote) => vote.memberId === userId && vote.type === voteType);

  const vote = (voteType: VoteType) => (): void => {
    voteIssue(
      { voteType },
      {
        onError: handleError,
        onSuccess: handleSuccess,
      }
    );
  };

  const handleSuccess = (): void => {
    setIsWaitingForSse(true);

    setHandler(
      handler.onIssueVotedEvent(({ data }) => {
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
    <Flex>
      {issue.status === IssueStatus.OPENED && (
        <Button isDisabled={!canVote(VoteType.DOWN)} onClick={vote(VoteType.DOWN)}>
          -
        </Button>
      )}
      <Text>
        {issue.votes.reduce((points, vote) => points + 1 * (vote.type === VoteType.UP ? 1 : -1), 0)}{' '}
        points
      </Text>
      {issue.status === IssueStatus.OPENED && (
        <Button isDisabled={!canVote(VoteType.UP)} onClick={vote(VoteType.UP)}>
          +
        </Button>
      )}
    </Flex>
  );
};
