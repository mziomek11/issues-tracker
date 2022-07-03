import { Route, Routes } from 'react-router-dom';
import { ChakraProvider } from '@chakra-ui/react';
import { paths } from '@shared/consts/routing';
import { HomePage } from '@shared/pages';
import { CustomQueryClientProvider } from '@shared/providers/query';
import { UserProvider } from '@users/contexts';
import { LoginPage, RegisterPage, UserActivationPage } from '@users/pages';
import { CreateOrganizationPage, OrganizationsPage } from 'organizations/pages';
import { SseProvider } from '@notifications/contexts';

export const App: React.FC = () => {
  return (
    <CustomQueryClientProvider>
      <UserProvider>
        <SseProvider>
          <ChakraProvider>
            <Routes>
              <Route path={paths['shared.home']} element={<HomePage />} />
              <Route path={paths['users.login']} element={<LoginPage />} />
              <Route path={paths['users.register']} element={<RegisterPage />} />
              <Route path={paths['users.activation']} element={<UserActivationPage />} />
              <Route path={paths['organizations.list']} element={<OrganizationsPage />} />
              <Route path={paths['organizations.create']} element={<CreateOrganizationPage />} />
            </Routes>
          </ChakraProvider>
        </SseProvider>
      </UserProvider>
    </CustomQueryClientProvider>
  );
};
