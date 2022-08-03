import { OrganizationsPage } from '@organizations/pages';
import { useUser } from '@users/contexts';
import { LoginPage } from '@users/pages';

export const HomePage: React.FC = () => {
  const { isLoggedIn } = useUser();

  return isLoggedIn ? <OrganizationsPage /> : <LoginPage />;
};
