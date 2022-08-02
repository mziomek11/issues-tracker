import { useState } from 'react';
import { Text, Button, useToast, Flex } from '@chakra-ui/react';
import { IssueDetailsCommentDto, IssueDetailsDto } from '@issues/dtos';
import { IssueStatus, VoteType } from '@issues/enums';
import { IssuesCommentDetailsParams } from '@issues/types';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { AxiosError } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { useUser } from '@users/contexts';
import { useVoteComment } from '@issues/hooks';

export interface CommentVotesProps {
  params: IssuesCommentDetailsParams;
  comment: IssueDetailsCommentDto;
  issue: IssueDetailsDto;
}

export const CommentVotes: React.FC<CommentVotesProps> = ({ params, comment, issue }) => {
  const toast = useToast();
  const { userId } = useUser();
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: voteComment, isLoading } = useVoteComment(params);
  useSseSubscription(handler);

  const canVote = (voteType: VoteType): boolean =>
    !isLoading &&
    !isWaitingForSse &&
    !comment.votes.some((vote) => vote.memberId === userId && vote.type === voteType);

  const vote = (voteType: VoteType) => (): void => {
    voteComment(
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
      handler.onIssueCommentVotedEvent(({ data }) => {
        if (data.commentId === params.commentId && data.memberId === userId) {
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
        {comment.votes.reduce(
          (points, vote) => points + 1 * (vote.type === VoteType.UP ? 1 : -1),
          0
        )}{' '}
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
