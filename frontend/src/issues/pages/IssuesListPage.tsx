import { VStack } from '@chakra-ui/react';
import { FC } from 'react';
import { Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';
import { IssuesList } from '@issues/components';
import { IssuesListParams } from '@issues/types';

export const IssuesListPage: FC = () => {
  const params = useParams<IssuesListParams>();

  return (
    <Layout>
      <VStack>
        <IssuesList params={params} />
      </VStack>
    </Layout>
  );
};
