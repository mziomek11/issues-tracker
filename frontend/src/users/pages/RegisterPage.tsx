import { Box, Center, Container, Text, VStack } from '@chakra-ui/react';
import { Header, RegisterForm } from '@users/components';

export const RegisterPage: React.FC = () => {
  return (
    <Container maxW="80vw" height="100vh">
      <Header />
      <Center>
        <Box borderWidth="2px" maxWidth="max-content" p={8}>
          <VStack justifyContent="center">
            <Text fontSize="6xl">Register</Text>
            <RegisterForm />
          </VStack>
        </Box>
      </Center>
    </Container>
  );
};
