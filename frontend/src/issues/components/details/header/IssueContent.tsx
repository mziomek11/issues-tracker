import {
  Flex,
  Text,
  Button,
  IconButton,
  HStack,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Textarea,
  VStack,
  Box,
} from '@chakra-ui/react';
import { EditIcon } from '@chakra-ui/icons';
import { ChangeIssueContentDto, IssueDetailsDto } from '@issues/dtos';
import { IssueStatus } from '@issues/enums';
import { useChangeIssueContent } from '@issues/hooks';
import { IssuesDetailsParams } from '@issues/types';
import React, { FormEvent, useState } from 'react';
import { first, isEmpty } from 'lodash';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { AxiosError } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { useUser } from '@users/contexts';

export interface IssueContentProps {
  params: IssuesDetailsParams;
  issue: IssueDetailsDto;
}

export const IssueContent: React.FC<IssueContentProps> = ({ params, issue }) => {
  const { userId } = useUser();
  const [content, setContent] = useState<string | null>(null);
  const [contentError, setContentError] = useState<string | null>(null);
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: changeContent, isLoading } = useChangeIssueContent(params);
  useSseSubscription(handler);

  const toggleChangeContent = (): void => {
    setContent(content === null ? issue.content : null);
    setContentError(null);
  };

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>): void =>
    setContent(e.target.value);

  const handleSubmit = (e: FormEvent): void => {
    e.preventDefault();
    changeContent(
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
      handler.onIssueContentChangedEvent(({ data }) => {
        if (data.issueId === params.issueId && data.memberId === userId) {
          setIsWaitingForSse(false);
          setContent(null);
        }
      })
    );
  };

  const handleError = (
    e: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void =>
    applicationErrorHandler<ChangeIssueContentDto>()
      .onIssueContentSet(toggleChangeContent)
      .onGenericValidationFailed(({ details }) =>
        setContentError(isEmpty(details.content) ? null : first(details.content)!)
      )
      .onDefault(({ message }) => setContentError(message))
      .handleAxiosError(e);

  if (content === null) {
    return (
      <Flex justifyContent="space-between">
        <Text>{issue.content}</Text>
        <Box>
          {issue.status === IssueStatus.OPENED && (
            <IconButton
              aria-label="Change content"
              icon={<EditIcon />}
              onClick={toggleChangeContent}
            />
          )}
        </Box>
      </Flex>
    );
  }

  return (
    <VStack as="form" onSubmit={handleSubmit}>
      <FormControl isInvalid={!!contentError}>
        <FormLabel htmlFor="content">Content</FormLabel>
        <Textarea id="content" name="content" value={content} onChange={handleContentChange} />
        <FormErrorMessage>{contentError}</FormErrorMessage>
      </FormControl>

      <HStack>
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
      </HStack>
    </VStack>
  );
};
