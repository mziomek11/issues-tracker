import {
  Flex,
  Text,
  Button,
  Input,
  IconButton,
  HStack,
  FormControl,
  FormErrorMessage,
  FormLabel,
} from '@chakra-ui/react';
import { EditIcon } from '@chakra-ui/icons';
import { IssueDetailsDto, RenameIssueDto } from '@issues/dtos';
import { IssueStatus } from '@issues/enums';
import { useRenameIssue } from '@issues/hooks';
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

export interface IssueNameProps {
  params: IssuesDetailsParams;
  issue: IssueDetailsDto;
}

export const IssueName: React.FC<IssueNameProps> = ({ params, issue }) => {
  const { userId } = useUser();
  const [name, setName] = useState<string | null>(null);
  const [nameError, setNameError] = useState<string | null>(null);
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: rename, isLoading } = useRenameIssue(params);
  useSseSubscription(handler);

  const toggleRename = (): void => {
    setName(name === null ? issue.name : null);
    setNameError(null);
  };

  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>): void =>
    setName(e.target.value);

  const handleSubmit = (e: FormEvent): void => {
    e.preventDefault();
    rename(
      { name: name || '' },
      {
        onError: handleError,
        onSuccess: handleSuccess,
      }
    );
  };

  const handleSuccess = (): void => {
    setIsWaitingForSse(true);

    setHandler(
      handler.onIssueRenamedEvent(({ data }) => {
        if (data.issueId === params.issueId && data.memberId === userId) {
          setIsWaitingForSse(false);
          setName(null);
        }
      })
    );
  };

  const handleError = (
    e: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void =>
    applicationErrorHandler<RenameIssueDto>()
      .onIssueNameSet(toggleRename)
      .onGenericValidationFailed(({ details }) =>
        setNameError(isEmpty(details.name) ? null : first(details.name)!)
      )
      .onDefault(({ message }) => setNameError(message))
      .handleAxiosError(e);

  if (name === null) {
    return (
      <Flex>
        <Text>{issue.name}</Text>
        {issue.status === IssueStatus.OPENED && (
          <IconButton aria-label="Rename" icon={<EditIcon />} onClick={toggleRename} />
        )}
      </Flex>
    );
  }

  return (
    <HStack as="form" onSubmit={handleSubmit}>
      <FormControl isInvalid={!!nameError}>
        <FormLabel htmlFor="name">Name</FormLabel>
        <Input id="name" name="name" value={name} onChange={handleNameChange} />
        <FormErrorMessage>{nameError}</FormErrorMessage>
      </FormControl>

      <Flex>
        <Button onClick={toggleRename} isDisabled={isLoading || isWaitingForSse} type="button">
          Cancel
        </Button>
        <Button isLoading={isLoading || isWaitingForSse} type="submit">
          Rename
        </Button>
      </Flex>
    </HStack>
  );
};
