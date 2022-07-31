import { AxiosError } from 'axios';
import { EditIcon } from '@chakra-ui/icons';
import { FC, useState } from 'react';
import {
  Alert,
  AlertDescription,
  AlertIcon,
  Button,
  HStack,
  Spacer,
  Spinner,
  Stack,
  Tag,
  Text,
} from '@chakra-ui/react';
import { IssueType, VoteType } from '@issues/enums';
import { useIssueDetails } from '@issues/hooks/useIssueDetails';
import { IssueDetailsParams } from '@issues/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { IssueDetailsDto } from '@issues/dtos';

interface IssueDetailsProps extends IssueDetailsParams {}

export const IssueDetails: FC<IssueDetailsProps> = (params) => {
  const [error, setError] = useState('');

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler<IssueDetailsDto>()
      .onAuthInvalidJwt(({ message }) => setError(message))
      .onOrganizationAccessDenied(({ message }) => setError(message))
      .onOrganizationNotFound(({ message }) => setError(message))
      .onOrganizationProjectNotFound(({ message }) => setError(message))
      .onIssueNotFound(({ message }) => setError(message))
      .handleAxiosError(error);
  };

  const {
    data: details,
    isLoading,
    isSuccess,
    isError,
  } = useIssueDetails(params, {
    onError: handleError,
  });

  return (
    <>
      {isLoading && <Spinner />}
      <Stack width={'65%'}>
        {isSuccess && (
          <>
            <HStack alignItems={'center'}>
              <HStack>
                <Text fontSize={'4xl'}>{details?.data.name}</Text>
                <EditIcon />
              </HStack>
              <Spacer />
              <HStack>
                <Tag
                  size="md"
                  colorScheme={details?.data.type === IssueType.BUG ? 'red' : 'green'}
                  borderRadius="full"
                >
                  {details?.data.type}
                </Tag>
                <EditIcon />
              </HStack>
            </HStack>
            <HStack>
              <Text fontSize={'xl'}>{details?.data.name}</Text>
              <EditIcon />
            </HStack>
            <HStack>
              <Button variant={'outline'}>Close issue</Button>
              <Spacer />
              <HStack>
                <Button variant={'outline'}>-</Button>
                <Text>
                  {details?.data.votes.reduce((prev, curr) => {
                    if (curr.type === VoteType.UP) return prev + 1;
                    else return prev - 1;
                  }, 0)}{' '}
                  points
                </Text>
              </HStack>
              <Button variant={'outline'}>+</Button>
            </HStack>
          </>
        )}
        {isError && (
          <Alert status="error">
            <AlertIcon />
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}
      </Stack>
    </>
  );
};
