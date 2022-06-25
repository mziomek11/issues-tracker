import { Box, Center, Text, VStack } from '@chakra-ui/react';
import { Layout } from '@shared/components/Layout';
import { LoginForm } from '@users/components';

export const LoginPage: React.FC = () => {
  return (
    <Layout>
      <Center>
        <Box borderWidth="2px" maxWidth="max-content" p={8}>
          <VStack justifyContent="center">
            <Text fontSize="6xl">Login</Text>
            <LoginForm />
          </VStack>
        </Box>
      </Center>
    </Layout>
  );
};
