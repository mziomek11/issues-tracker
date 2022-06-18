import { Text, VStack } from '@chakra-ui/react';
import { LoginForm } from '@users/components';

export const LoginPage: React.FC = () => {
  return (
    <VStack justifyContent="center">
      <Text fontSize="6xl">Login</Text>
      <LoginForm />
    </VStack>
  );
};
