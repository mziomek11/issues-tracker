import { Container } from '@chakra-ui/react';
import { PropsWithChildren } from 'react';
import { Header } from './Header';

interface LayoutProps {}

export const Layout: React.FC<PropsWithChildren<LayoutProps>> = ({ children }) => {
  return (
    <Container maxW="80vw" height="100vh">
      <Header />
      {children}
    </Container>
  );
};
