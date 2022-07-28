import { Text, VStack } from '@chakra-ui/react';
import { CreateIssueForm } from '@issues/components';
import { IssuesListParams } from '@issues/types';
import { Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';
import { FC } from 'react';

export const CreateIssuePage: FC = () => {
  const params = useParams<IssuesListParams>();
  return (
    <Layout>
      <VStack>
        <Text fontSize="6xl">Issues</Text>
        <CreateIssueForm {...params} />
      </VStack>
    </Layout>
  );
};
