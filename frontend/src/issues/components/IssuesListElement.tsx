import { HStack, Spacer, Stack, Tag, Text } from '@chakra-ui/react';
import { FC } from 'react';
import { IssuesListElement as Props } from '@issues/dtos';

interface IssuesListElementProps extends Props {}
export const IssuesListElement: FC<IssuesListElementProps> = ({ name, type, creator, votes }) => {
  return (
    <Stack width="100%" padding={'10px 0'}>
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
          {votes.length} points
        </Text>
      </HStack>
    </Stack>
  );
};
