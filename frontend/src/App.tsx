import { Route, Routes } from 'react-router-dom';
import { ChakraProvider } from '@chakra-ui/react';
import { CustomQueryClientProvider } from '@shared/providers/query';
import { LoginPage, RegisterPage, UserActivationPage } from '@users/pages';
import { paths } from '@shared/consts/routing';
import { useAuthorization } from '@users/hooks/auth';

export const App: React.FC = () => {
  useAuthorization();
  return (
    <CustomQueryClientProvider>
      <ChakraProvider>
        <Routes>
          <Route path={paths['users.login']} element={<LoginPage />} />
          <Route path={paths['users.register']} element={<RegisterPage />} />
          <Route path={paths['users.activation']} element={<UserActivationPage />} />
        </Routes>
      </ChakraProvider>
    </CustomQueryClientProvider>
  );
};
