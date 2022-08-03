import { VStack } from '@chakra-ui/react';
import { OrganizationDetails } from '@organizations/components';
import { OrganizationParams } from '@organizations/types';
import { Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';

export const OrganizationDetailsPage: React.FC = () => {
  const params = useParams<OrganizationParams>();
  return (
    <Layout>
      <VStack>
        <OrganizationDetails {...params} />
      </VStack>
    </Layout>
  );
};
