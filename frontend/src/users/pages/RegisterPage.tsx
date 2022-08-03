import { FormBox, Layout } from '@shared/components';
import { RegisterForm } from '@users/components';

export const RegisterPage: React.FC = () => {
  return (
    <Layout>
      <FormBox heading="Register">
        <RegisterForm />
      </FormBox>
    </Layout>
  );
};
