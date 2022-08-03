import { Box } from '@chakra-ui/react';
import { PropsWithChildren } from 'react';
import { Header } from './Header';
import { Footer } from './Footer';
import { Container } from './Container';

interface LayoutProps {}

export const Layout: React.FC<PropsWithChildren<LayoutProps>> = ({ children }) => {
  return (
    <Box display="flex" flexDirection="column" height="100vh">
      <Header />
      <Container flexGrow="1">{children}</Container>
      <Footer />
    </Box>
  );
};
