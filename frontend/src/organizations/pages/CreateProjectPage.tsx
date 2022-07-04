import { Text, VStack } from '@chakra-ui/react';
import { ProjectForm } from '@organizations/components';
import { OrganizationParams } from '@organizations/types';
import { Layout } from '@shared/components/Layout';
import { useParams } from '@shared/hooks/api';

export const CreateProjectPage: React.FC = () => {
  const params = useParams<OrganizationParams>();

  return (
    <Layout>
      <VStack>
        <Text fontSize="6xl">Organizations</Text>
        <ProjectForm {...params} />
      </VStack>
    </Layout>
  );
};
