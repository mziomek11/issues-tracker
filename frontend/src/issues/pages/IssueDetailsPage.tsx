import { VStack } from '@chakra-ui/react';
import { FC } from 'react';
import { Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';
import { IssueDetailsParams } from '@issues/types';
import { IssueDetails } from '@issues/components';

export const IssueDetailsPage: FC = () => {
  const params = useParams<IssueDetailsParams>();

  return (
    <Layout>
      <VStack>
        <IssueDetails {...params} />
      </VStack>
    </Layout>
  );
};
