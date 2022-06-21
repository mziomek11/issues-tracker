import { reverse } from '@shared/helpers/routing';
import { tokenExpirationCheck } from '@shared/helpers/tokenExpiration';
import { useNavigate } from 'react-router-dom';

export const useCheckToken = () => {
  const navigate = useNavigate();
  const currrentJwt = localStorage.getItem('JWT');
  return () => {
    if (!currrentJwt || !tokenExpirationCheck(currrentJwt)) navigate(reverse('users.login'));
  };
};
