import { Button, ButtonGroup, Center, Flex, Heading, Spacer } from '@chakra-ui/react';
import { reverse } from '@shared/helpers/routing';
import { useNavigate } from 'react-router-dom';

export const Header: React.FC = () => {
  const navigate = useNavigate();

  const navigateToLogin = (): void => navigate(reverse('users.login'));
  const navigateToRegister = (): void => navigate(reverse('users.register'));

  return (
    <Flex minWidth="max-content">
      <Center>
        <Heading size="md">IssuesTracker</Heading>
      </Center>
      <Spacer />
      <ButtonGroup>
        <Button variant="ghost" onClick={navigateToLogin}>
          Login
        </Button>
        <Button variant="ghost" onClick={navigateToRegister}>
          Register
        </Button>
      </ButtonGroup>
    </Flex>
  );
};
