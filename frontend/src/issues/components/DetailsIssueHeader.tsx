import { Flex, VStack, Text, Button, Box } from '@chakra-ui/react';
import { IssueDetailsDto } from '@issues/dtos';
import { IssueStatus } from '@issues/enums';

export interface DetailsIssueHeaderProps {
  issue: IssueDetailsDto;
}

export const DetailsIssueHeader: React.FC<DetailsIssueHeaderProps> = ({ issue }) => {
  return (
    <VStack>
      <Flex>{issue.name}</Flex>
      <Text>{issue.type}</Text>
      <Flex>
        <Box>{issue.status === IssueStatus.OPENED && <Button>Close</Button>}</Box>

        <Flex>
          <Button>-</Button>
          <Text>{issue.votes.length} points</Text>
          <Button>+</Button>
        </Flex>
      </Flex>
    </VStack>
  );
};
