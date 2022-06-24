import { Button, Center, Flex, Heading, Spacer } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';
import { useJwt } from '@users/contexts';

export const Header: React.FC = () => {
  const { jwt, removeJwt } = useJwt();

  return (
    <Flex minWidth="max-content">
      <Center>
        <Heading size="md">IssuesTracker</Heading>
      </Center>
      <Spacer />
      <Flex gap="3">
        {jwt ? (
          <>
            <Button variant="ghost" onClick={removeJwt}>
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
