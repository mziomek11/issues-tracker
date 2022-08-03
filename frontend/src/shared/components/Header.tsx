import { Button, Center, Flex, Heading, HStack, Spacer } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';
import { useUser } from '@users/contexts';
import { Container } from './Container';

export const Header: React.FC = () => {
  const { isLoggedIn, logoutUser } = useUser();

  return (
    <Flex py="4">
      <Container display="flex" alignItems="center" justifyContent="space-between">
        <Center>
          <Link to={reverse('shared.home')}>
            <Heading size="md">IssuesTracker</Heading>
          </Link>
        </Center>
        <Spacer />
        <HStack>
          {isLoggedIn ? (
            <>
              <Button as={Link} to={reverse('organizations.list')} variant="outline">
                Organizations
              </Button>
              <Button as={Link} to={reverse('organizations.member.accept')} variant="outline">
                Invitations
              </Button>
              <Button variant="outline" onClick={logoutUser}>
                Logout
              </Button>
            </>
          ) : (
            <>
              <Button as={Link} to={reverse('users.login')} variant="outline">
                Login
              </Button>
              <Button as={Link} to={reverse('users.register')} variant="outline">
                Register
              </Button>
            </>
          )}
        </HStack>
      </Container>
    </Flex>
  );
};
