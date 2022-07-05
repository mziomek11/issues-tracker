import { Text, VStack } from '@chakra-ui/react';
import { Layout } from '@shared/components/Layout';
import { OrganizationForm } from 'organizations/components';

export const CreateOrganizationPage: React.FC = () => {
  return (
    <Layout>
      <VStack>
        <Text fontSize="6xl">Organizations</Text>
        <OrganizationForm />
      </VStack>
    </Layout>
  );
};
