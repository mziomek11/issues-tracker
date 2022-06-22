import { Text, VStack } from '@chakra-ui/react';
import { UserActivation } from '@users/components/UserActivation';
import { useParams } from '@shared/hooks/api';
import { UserActivationParams } from '@users/types/activation';

export const UserActivationPage: React.FC = () => {
  const { userId, activationToken } = useParams<UserActivationParams>();

  return (
    <VStack justifyContent="center">
      <Text fontSize="6xl">User activation</Text>
      <UserActivation userId={userId} activationToken={activationToken} />
    </VStack>
  );
};
