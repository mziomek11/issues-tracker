import { Route, Routes } from 'react-router-dom';
import { ChakraProvider } from '@chakra-ui/react';
import { paths } from '@shared/consts/routing';
import { CustomQueryClientProvider } from '@shared/providers/query';
import { UserProvider } from '@users/contexts';
import { useAuthorization } from '@users/hooks/auth';
import { LoginPage, RegisterPage, UserActivationPage } from '@users/pages';

export const App: React.FC = () => {
  useAuthorization();
  return (
    <CustomQueryClientProvider>
      <UserProvider>
        <ChakraProvider>
          <Routes>
            <Route path={paths['users.login']} element={<LoginPage />} />
            <Route path={paths['users.register']} element={<RegisterPage />} />
            <Route path={paths['users.activation']} element={<UserActivationPage />} />
          </Routes>
        </ChakraProvider>
      </UserProvider>
    </CustomQueryClientProvider>
  );
};
