import { Text, VStack } from '@chakra-ui/react';
import { MemberAcceptance } from '@organizations/components';
import { Layout } from '@shared/components';

export const MemberAcceptancePage: React.FC = () => {
  return (
    <Layout>
      <VStack>
        <Text fontSize="4xl">Invitations</Text>
        <MemberAcceptance />
      </VStack>
    </Layout>
  );
};
