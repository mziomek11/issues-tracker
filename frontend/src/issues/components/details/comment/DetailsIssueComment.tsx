import { useState } from 'react';
import { Text, Box, Flex, Button, useToast, HStack } from '@chakra-ui/react';
import { IssueDetailsCommentDto, IssueDetailsDto } from '@issues/dtos';
import { CommentStatus, IssueStatus } from '@issues/enums';
import { useUser } from '@users/contexts';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useHideComment } from '@issues/hooks';
import { useSseSubscription } from '@notifications/hooks/api';
import { AxiosError } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { IssuesDetailsParams } from '@issues/types';
import { CommentContent } from './CommentContent';
import { CommentVotes } from './CommentVotes';

export interface DetailsIssueCommentProps {
  params: IssuesDetailsParams;
  comment: IssueDetailsCommentDto;
  issue: IssueDetailsDto;
}

export const DetailsIssueComment: React.FC<DetailsIssueCommentProps> = ({
  params,
  comment,
  issue,
}) => {
  const [isHidden, setIsHidden] = useState(comment.status === CommentStatus.HIDDEN);
  const toast = useToast();
  const { userId } = useUser();
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: hideComment, isLoading: isHidingComment } = useHideComment({
    ...params,
    commentId: comment.id,
  });
  useSseSubscription(handler);

  const handleShow = (): void => setIsHidden(false);
  const handleHide = (): void => {
    if (comment.status === CommentStatus.HIDDEN) {
      setIsHidden(true);
      return;
    }

    hideComment(null, { onSuccess: handleSuccess, onError: handleError });
  };

  const handleSuccess = (): void => {
    setIsWaitingForSse(true);

    setHandler(
      handler.onIssueCommentHiddenEvent(({ data }) => {
        if (data.commentId === comment.id && data.memberId === userId) {
          setIsWaitingForSse(false);
          setIsHidden(true);
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

  if (isHidden) {
    return (
      <HStack border="1px" borderColor="gray.200" p="4" w="full">
        <Button onClick={handleShow}>Show</Button>
        <Text>Comment hidden</Text>
      </HStack>
    );
  }

  return (
    <Box border="1px" borderColor="gray.200" p="4" w="full">
      <CommentContent
        comment={comment}
        issue={issue}
        params={{ ...params, commentId: comment.id }}
      />
      <Text fontSize="sm">Commented by {comment.creator.email}</Text>

      <Flex mt="8" justify="space-between">
        <Box>
          {(issue.status === IssueStatus.OPENED || comment.status === CommentStatus.HIDDEN) && (
            <Button onClick={handleHide} isLoading={isHidingComment || isWaitingForSse}>
              Hide
            </Button>
          )}
        </Box>

        <CommentVotes
          comment={comment}
          issue={issue}
          params={{ ...params, commentId: comment.id }}
        />
      </Flex>
    </Box>
  );
};
