import { Box, Center, Text, VStack } from '@chakra-ui/react';
import { Layout } from '@shared/components';
import { RegisterForm } from '@users/components';

export const RegisterPage: React.FC = () => {
  return (
    <Layout>
      <Center>
        <Box borderWidth="2px" maxWidth="max-content" p={8}>
          <VStack justifyContent="center">
            <Text fontSize="6xl">Register</Text>
            <RegisterForm />
          </VStack>
        </Box>
      </Center>
    </Layout>
  );
};
