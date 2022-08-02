import { Flex, VStack, Text, Button } from '@chakra-ui/react';
import { IssueDetailsDto } from '@issues/dtos';
import { IssuesDetailsParams } from '@issues/types';
import { IssueName } from './IssueName';
import { IssueContent } from './IssueContent';
import { IssueType } from './IssueType';
import { IssueStatus } from './IssueStatus';

export interface DetailsIssueHeaderProps {
  params: IssuesDetailsParams;
  issue: IssueDetailsDto;
}

export const DetailsIssueHeader: React.FC<DetailsIssueHeaderProps> = ({ params, issue }) => {
  return (
    <VStack>
      <Flex>
        <IssueName issue={issue} params={params} />
        <IssueType issue={issue} params={params} />
      </Flex>
      <IssueContent issue={issue} params={params} />

      <Flex>
        <IssueStatus issue={issue} params={params} />

        <Flex>
          <Button>-</Button>
          <Text>{issue.votes.length} points</Text>
          <Button>+</Button>
        </Flex>
      </Flex>
    </VStack>
  );
};
