import { Navigate, Outlet } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';
import { useUser } from '@users/contexts';

interface SecureRouteParams {
  userRequired: boolean;
}
export const SecureRoute: React.FC<SecureRouteParams> = ({ userRequired }) => {
  const { isLoggedIn } = useUser();
  const redirectPath = reverse(userRequired ? 'users.login' : 'shared.home');

  if (!isLoggedIn === userRequired) return <Navigate to={redirectPath} replace />;

  return <Outlet />;
};
