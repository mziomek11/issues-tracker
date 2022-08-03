import { Box, Flex, Text } from '@chakra-ui/react';
import { IssueDetailsDto } from '@issues/dtos';
import { IssuesDetailsParams } from '@issues/types';
import { IssueName } from './IssueName';
import { IssueContent } from './IssueContent';
import { IssueType } from './IssueType';
import { IssueStatus } from './IssueStatus';
import { IssueVotes } from './IssueVotes';

export interface DetailsIssueHeaderProps {
  params: IssuesDetailsParams;
  issue: IssueDetailsDto;
}

export const DetailsIssueHeader: React.FC<DetailsIssueHeaderProps> = ({ params, issue }) => {
  return (
    <Box border="1px" borderColor="gray.200" p="4">
      <Flex align="flex-start" justify="space-between">
        <IssueName issue={issue} params={params} />
        <IssueType issue={issue} params={params} />
      </Flex>

      <Text fontSize="sm">Opened by {issue.creator.email}</Text>

      <Box my="8">
        <IssueContent issue={issue} params={params} />
      </Box>

      <Flex justify="space-between">
        <IssueStatus issue={issue} params={params} />
        <IssueVotes issue={issue} params={params} />
      </Flex>
    </Box>
  );
};
