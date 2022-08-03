import { CreateIssueForm } from '@issues/components';
import { IssuesListParams } from '@issues/types';
import { FormBox, Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';
import { FC } from 'react';

export const CreateIssuePage: FC = () => {
  const params = useParams<IssuesListParams>();
  return (
    <Layout>
      <FormBox heading="Open issue">
        <CreateIssueForm {...params} />
      </FormBox>
    </Layout>
  );
};
