import { Text, VStack } from '@chakra-ui/react';
import { RegisterForm } from '../components/RegisterForm';

export const RegisterPage: React.FC = () => {
  return (
    <VStack justifyContent="center">
      <Text fontSize="6xl">Register</Text>
      <RegisterForm />
    </VStack>
  );
};
