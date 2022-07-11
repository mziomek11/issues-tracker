import { Text, VStack } from '@chakra-ui/react';
import { MemberInvitationForm } from '@organizations/components';
import { OrganizationParams } from '@organizations/types';
import { Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';

export const MemberInvitationPage: React.FC = () => {
  const params = useParams<OrganizationParams>();
  return (
    <Layout>
      <VStack>
        <Text fontSize="6xl">Organizations</Text>
        <MemberInvitationForm {...params} />
      </VStack>
    </Layout>
  );
};
