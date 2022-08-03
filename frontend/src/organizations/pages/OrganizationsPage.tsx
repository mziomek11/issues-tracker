import { Text, VStack } from '@chakra-ui/react';
import { UserOrganizationsList } from '@organizations/components';
import { Layout } from '@shared/components/Layout';

export const OrganizationsPage: React.FC = () => {
  return (
    <Layout>
      <VStack>
        <Text fontSize="4xl" mb="6">
          Organizations
        </Text>
        <UserOrganizationsList />
      </VStack>
    </Layout>
  );
};
