import { ChakraProvider } from '@chakra-ui/react';
import { Route, Routes } from 'react-router-dom';
import { CustomQueryClientProvider } from './shared/components/CustomQueryClientProvider';
import { reverse } from './shared/helpers/reverse';
import { LoginPage, RegisterPage } from './users/pages';
import './style.css';

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
