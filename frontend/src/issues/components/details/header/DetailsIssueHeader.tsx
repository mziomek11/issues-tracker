import { Flex, VStack } from '@chakra-ui/react';
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
    <VStack>
      <Flex>
        <IssueName issue={issue} params={params} />
        <IssueType issue={issue} params={params} />
      </Flex>
      <IssueContent issue={issue} params={params} />

      <Flex>
        <IssueStatus issue={issue} params={params} />
        <IssueVotes issue={issue} params={params} />
      </Flex>
    </VStack>
  );
};
