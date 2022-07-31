import { Spinner, Text } from '@chakra-ui/react';
import { useIssueDetails } from '@issues/hooks/useIssueDetails';
import { IssueDetailsParams } from '@issues/types';
import { FC } from 'react';

interface IssueDetailsProps extends IssueDetailsParams {}

export const IssueDetails: FC<IssueDetailsProps> = (params) => {
  const { data, isLoading } = useIssueDetails(params, {});

  if (isLoading) return <Spinner />;

  return <Text>{data?.data.name}</Text>;
};
