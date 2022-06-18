import { ChakraProvider } from '@chakra-ui/react';
import { Route, Routes } from 'react-router-dom';
import { CustomQueryClientProvider } from '@shared/providers/query';
import { reverse } from '@shared/helpers/routing/reverse';
import { LoginPage, RegisterPage } from '@users/pages';

export const App: React.FC = () => {
  return (
    <CustomQueryClientProvider>
      <ChakraProvider>
        <Routes>
          <Route path={reverse('users.login')} element={<LoginPage />} />
          <Route path={reverse('users.register')} element={<RegisterPage />} />
        </Routes>
      </ChakraProvider>
    </CustomQueryClientProvider>
  );
};
