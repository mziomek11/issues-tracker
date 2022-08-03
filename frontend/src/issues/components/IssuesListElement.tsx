import { Link } from 'react-router-dom';
import { HStack, Spacer, Stack, Tag, Text } from '@chakra-ui/react';
import { FC } from 'react';
import { IssuesListElement as IssuesListElementDto } from '@issues/dtos';
import { reverse } from '@shared/helpers/routing';
import { VoteType } from '@issues/enums';

interface IssuesListElementProps extends IssuesListElementDto {
  organizationId: string;
  projectId: string;
}

export const IssuesListElement: FC<IssuesListElementProps> = ({
  id,
  name,
  type,
  creator,
  votes,
  organizationId,
  projectId,
}) => {
  return (
    <Stack
      border="1px"
      borderColor="gray.200"
      as={Link}
      to={reverse({
        path: 'issues.details',
        params: { issueId: id, organizationId, projectId },
      })}
      width="100%"
      my="4"
      p="4"
    >
      <HStack alignItems={'start'}>
        <Text fontSize={'lg'}>{name}</Text>

        <Spacer />
        <Tag size="md" colorScheme={type === 'BUG' ? 'red' : 'green'} borderRadius="full">
          {type}
        </Tag>
      </HStack>
      <HStack alignItems={'end'}>
        <Text fontSize={'xs'}>by {creator.email}</Text>
        <Spacer />
        <Text fontSize={'xs'} paddingRight="5px">
          {votes.reduce((points, vote) => points + 1 * (vote.type === VoteType.UP ? 1 : -1), 0)}{' '}
          points
        </Text>
      </HStack>
    </Stack>
  );
};
