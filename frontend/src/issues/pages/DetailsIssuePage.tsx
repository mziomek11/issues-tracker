import { Spinner, VStack } from '@chakra-ui/react';
import { DetailsIssueHeader } from '@issues/components/details/header';
import { useIssue } from '@issues/hooks';
import { IssuesDetailsParams } from '@issues/types';
import { Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';
import { FC } from 'react';

export const DetailsIssuePage: FC = () => {
  const params = useParams<IssuesDetailsParams>();
  const issueQuery = useIssue(params);

  return (
    <Layout>
      <VStack>
        {issueQuery.isLoading && <Spinner />}
        {issueQuery.isSuccess && (
          <DetailsIssueHeader params={params} issue={issueQuery.data.data} />
        )}
      </VStack>
    </Layout>
  );
};
