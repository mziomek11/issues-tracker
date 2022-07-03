import { Button, Center, Flex, Heading, Spacer } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';
import { useUser } from '@users/contexts';

export const Header: React.FC = () => {
  const { isLoggedIn, logoutUser } = useUser();

  return (
    <Flex minWidth="max-content">
      <Center>
        <Link to={reverse('shared.home')}>
          <Heading size="md">IssuesTracker</Heading>
        </Link>
      </Center>
      <Spacer />
      <Flex gap="3">
        {isLoggedIn ? (
          <>
            <Link to={reverse('organizations.list')}>
              <Button variant="ghost">Organizations</Button>
            </Link>
            <Link to={reverse('organizations.create')}>
              <Button variant="ghost">Add Organization</Button>
            </Link>
            <Button variant="ghost" onClick={logoutUser}>
              Logout
            </Button>
          </>
        ) : (
          <>
            <Link to={reverse('users.login')}>
              <Button variant="ghost">Login</Button>
            </Link>
            <Link to={reverse('users.register')}>
              <Button variant="ghost">Register</Button>
            </Link>
          </>
        )}
      </Flex>
    </Flex>
  );
};
