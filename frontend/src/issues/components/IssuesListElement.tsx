import { Link } from 'react-router-dom';
import { HStack, Link as ChakraLink, Spacer, Stack, Tag, Text } from '@chakra-ui/react';
import { FC } from 'react';
import { IssuesListElement as IssuesListElementDto } from '@issues/dtos';
import { reverse } from '@shared/helpers/routing';

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
    <Stack width="100%" padding={'10px 0'}>
      <HStack alignItems={'start'}>
        <ChakraLink
          as={Link}
          to={reverse({
            path: 'issues.details',
            params: { issueId: id, organizationId, projectId },
          })}
          fontSize={'lg'}
        >
          {name}
        </ChakraLink>
        <Spacer />
        <Tag size="md" colorScheme={type === 'BUG' ? 'red' : 'green'} borderRadius="full">
          {type}
        </Tag>
      </HStack>
      <HStack alignItems={'end'}>
        <Text fontSize={'xs'}>by {creator.email}</Text>
        <Spacer />
        <Text fontSize={'xs'} paddingRight="5px">
          {votes.length} points
        </Text>
      </HStack>
    </Stack>
  );
};
