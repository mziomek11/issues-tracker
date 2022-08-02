import { FormEvent, useState } from 'react';
import { ChangeCommentContentDto, IssueDetailsCommentDto, IssueDetailsDto } from '@issues/dtos';
import { IssuesCommentDetailsParams } from '@issues/types';
import { useUser } from '@users/contexts';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useChangeCommentContent } from '@issues/hooks/useChangeCommentContent';
import { useSseSubscription } from '@notifications/hooks/api';
import { AxiosError } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { first, isEmpty } from 'lodash';
import {
  Flex,
  Text,
  IconButton,
  HStack,
  FormControl,
  FormLabel,
  Textarea,
  FormErrorMessage,
  Button,
} from '@chakra-ui/react';
import { EditIcon } from '@chakra-ui/icons';
import { IssueStatus } from '@issues/enums';

export interface CommentContentProps {
  params: IssuesCommentDetailsParams;
  comment: IssueDetailsCommentDto;
  issue: IssueDetailsDto;
}

export const CommentContent: React.FC<CommentContentProps> = ({ comment, params, issue }) => {
  const { userId } = useUser();
  const [content, setContent] = useState<string | null>(null);
  const [contentError, setContentError] = useState<string | null>(null);
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: changeCommentContent, isLoading } = useChangeCommentContent(params);
  useSseSubscription(handler);

  const toggleChangeContent = (): void => {
    setContent(content === null ? comment.content : null);
    setContentError(null);
  };

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>): void =>
    setContent(e.target.value);

  const handleSubmit = (e: FormEvent): void => {
    e.preventDefault();
    changeCommentContent(
      { content: content || '' },
      {
        onError: handleError,
        onSuccess: handleSuccess,
      }
    );
  };

  const handleSuccess = (): void => {
    setIsWaitingForSse(true);

    setHandler(
      handler.onIssueCommentContentChangedEvent(({ data }) => {
        if (data.commentId === params.commentId && data.memberId === userId) {
          setIsWaitingForSse(false);
          setContent(null);
        }
      })
    );
  };

  const handleError = (
    e: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void =>
    applicationErrorHandler<ChangeCommentContentDto>()
      .onIssueNameSet(toggleChangeContent)
      .onGenericValidationFailed(({ details }) =>
        setContentError(isEmpty(details.content) ? null : first(details.content)!)
      )
      .onDefault(({ message }) => setContentError(message))
      .handleAxiosError(e);

  if (content === null) {
    return (
      <Flex>
        <Text>{comment.content}</Text>
        {issue.status === IssueStatus.OPENED && (
          <IconButton
            aria-label="Change content"
            icon={<EditIcon />}
            onClick={toggleChangeContent}
          />
        )}
      </Flex>
    );
  }

  return (
    <HStack as="form" onSubmit={handleSubmit}>
      <FormControl isInvalid={!!contentError}>
        <FormLabel htmlFor="commentContent">Content</FormLabel>
        <Textarea
          id="commentContent"
          name="commentContent"
          value={content}
          onChange={handleContentChange}
        />
        <FormErrorMessage>{contentError}</FormErrorMessage>
      </FormControl>

      <Flex>
        <Button
          onClick={toggleChangeContent}
          isDisabled={isLoading || isWaitingForSse}
          type="button"
        >
          Cancel
        </Button>
        <Button isLoading={isLoading || isWaitingForSse} type="submit">
          Change content
        </Button>
      </Flex>
    </HStack>
  );
};
