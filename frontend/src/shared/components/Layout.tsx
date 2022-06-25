import { Container } from '@chakra-ui/react';
import { Header } from './Header';

type LayoutProps = {
  children: JSX.Element;
};
export const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <Container maxW="80vw" height="100vh">
      <Header />
      {children}
    </Container>
  );
};
