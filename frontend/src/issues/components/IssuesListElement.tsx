import { HStack, Spacer, Stack, Tag, Text } from '@chakra-ui/react';
import { FC } from 'react';
import { IssueListElementDto } from '@issues/dtos';
import { Link } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';
import { IssueType } from '@issues/enums';

interface IssuesListElementProps extends IssueListElementDto {}

export const IssuesListElement: FC<IssuesListElementProps> = ({
  name,
  type,
  creator,
  votes,
  id,
  organization,
  project,
}) => {
  const detailsParams = {
    issueId: id,
    projectId: project.id,
    organizationId: organization.id,
  };

  return (
    <Link to={reverse({ path: 'issues.details', params: detailsParams })}>
      <Stack width="100%" padding={'10px 0'}>
        <HStack>
          <Text fontSize={'lg'}>{name}</Text>
          <Spacer />
          <Tag size="md" colorScheme={type === IssueType.BUG ? 'red' : 'green'} borderRadius="full">
            {type}
          </Tag>
        </HStack>
        <HStack>
          <Text fontSize={'xs'}>by {creator.email}</Text>
          <Spacer />
          <Text fontSize={'xs'} paddingRight="5px">
            {votes.length} points
          </Text>
        </HStack>
      </Stack>
    </Link>
  );
};
