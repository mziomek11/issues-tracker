import { ProjectForm } from '@organizations/components';
import { OrganizationParams } from '@organizations/types';
import { FormBox } from '@shared/components';
import { Layout } from '@shared/components/Layout';
import { useParams } from '@shared/hooks/api';

export const CreateProjectPage: React.FC = () => {
  const params = useParams<OrganizationParams>();

  return (
    <Layout>
      <FormBox heading="Create project">
        <ProjectForm {...params} />
      </FormBox>
    </Layout>
  );
};
