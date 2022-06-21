import { Box, Center, Container, Text, VStack } from '@chakra-ui/react';
import { Header, LoginForm } from '@users/components';

export const LoginPage: React.FC = () => {
  return (
    <Container maxW="80vw" height="100vh">
      <Header />
      <Center>
        <Box borderWidth="2px" maxWidth="max-content" p={8}>
          <VStack justifyContent="center">
            <Text fontSize="6xl">Login</Text>
            <LoginForm />
          </VStack>
        </Box>
      </Center>
    </Container>
  );
};
