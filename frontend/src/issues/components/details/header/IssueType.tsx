import {
  Flex,
  Text,
  Button,
  IconButton,
  HStack,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Select,
} from '@chakra-ui/react';
import { EditIcon } from '@chakra-ui/icons';
import { ChangeIssueTypeDto, IssueDetailsDto } from '@issues/dtos';
import { IssueStatus, IssueType as IssueTypeEnum } from '@issues/enums';
import { useChangeIssueType } from '@issues/hooks';
import { IssuesDetailsParams } from '@issues/types';
import React, { FormEvent, useState } from 'react';
import { first, isEmpty, values } from 'lodash';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { AxiosError } from 'axios';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { useUser } from '@users/contexts';

export interface IssueTypeProps {
  params: IssuesDetailsParams;
  issue: IssueDetailsDto;
}

export const IssueType: React.FC<IssueTypeProps> = ({ params, issue }) => {
  const { userId } = useUser();
  const [type, setType] = useState<IssueTypeEnum | null>(null);
  const [typeError, setTypeError] = useState<string | null>(null);
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  const { mutate: changeIssueType, isLoading } = useChangeIssueType(params);
  useSseSubscription(handler);

  const toggleChangeType = (): void => {
    setType(type === null ? issue.type : null);
    setTypeError(null);
  };

  const handleTypeChange = (e: React.ChangeEvent<HTMLSelectElement>): void =>
    setType(e.target.value as IssueTypeEnum);

  const handleSubmit = (e: FormEvent): void => {
    e.preventDefault();
    changeIssueType(
      { type: type || IssueTypeEnum.BUG },
      {
        onError: handleError,
        onSuccess: handleSuccess,
      }
    );
  };

  const handleSuccess = (): void => {
    setIsWaitingForSse(true);

    setHandler(
      handler.onIssueTypeChangedEvent(({ data }) => {
        if (data.issueId === params.issueId && data.memberId === userId) {
          setIsWaitingForSse(false);
          setType(null);
        }
      })
    );
  };

  const handleError = (
    e: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void =>
    applicationErrorHandler<ChangeIssueTypeDto>()
      .onIssueTypeSet(toggleChangeType)
      .onGenericValidationFailed(({ details }) =>
        setTypeError(isEmpty(details.type) ? null : first(details.type)!)
      )
      .onDefault(({ message }) => setTypeError(message))
      .handleAxiosError(e);

  if (type === null) {
    return (
      <Flex>
        <Text>{issue.type}</Text>
        {issue.status === IssueStatus.OPENED && (
          <IconButton aria-label="Change type" icon={<EditIcon />} onClick={toggleChangeType} />
        )}
      </Flex>
    );
  }

  return (
    <HStack as="form" onSubmit={handleSubmit}>
      <FormControl isInvalid={!!typeError}>
        <FormLabel htmlFor="type">Type</FormLabel>
        <Select id="type" name="type" value={type} onChange={handleTypeChange}>
          {values(IssueTypeEnum).map((type) => (
            <option key={type} value={type}>
              {type}
            </option>
          ))}
        </Select>
        <FormErrorMessage>{typeError}</FormErrorMessage>
      </FormControl>

      <Flex>
        <Button onClick={toggleChangeType} isDisabled={isLoading || isWaitingForSse} type="button">
          Cancel
        </Button>
        <Button isLoading={isLoading || isWaitingForSse} type="submit">
          Change type
        </Button>
      </Flex>
    </HStack>
  );
};
