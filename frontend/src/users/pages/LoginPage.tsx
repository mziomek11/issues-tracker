import { FormBox, Layout } from '@shared/components';
import { LoginForm } from '@users/components';

export const LoginPage: React.FC = () => {
  return (
    <Layout>
      <FormBox heading="Login">
        <LoginForm />
      </FormBox>
    </Layout>
  );
};
