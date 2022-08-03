import { FormBox, Layout } from '@shared/components';
import { OrganizationForm } from '@organizations/components';

export const CreateOrganizationPage: React.FC = () => {
  return (
    <Layout>
      <FormBox heading="Create organization">
        <OrganizationForm />
      </FormBox>
    </Layout>
  );
};
