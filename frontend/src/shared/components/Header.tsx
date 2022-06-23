import { ButtonGroup, Center, Flex, Heading, Spacer } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';

export const Header: React.FC = () => {
  return (
    <Flex minWidth="max-content">
      <Center>
        <Heading size="md">IssuesTracker</Heading>
      </Center>
      <Spacer />
      <ButtonGroup>
        <Link to={reverse('users.login')}>Login</Link>
        <Link to={reverse('users.register')}>Register</Link>
      </ButtonGroup>
    </Flex>
  );
};
