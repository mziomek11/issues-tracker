import { Text, VStack } from '@chakra-ui/react';
import { UserOrganizationsList } from '@organizations/components';
import { Layout } from '@shared/components/Layout';

export const OrganizationsPage = () => {
  return (
    <Layout>
      <VStack>
        <Text fontSize="6xl">Organizations</Text>
        <UserOrganizationsList />
      </VStack>
    </Layout>
  );
};
