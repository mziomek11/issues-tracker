import { Params, useParams } from 'react-router-dom';
import { Text, VStack } from '@chakra-ui/react';
import { UserActivation } from '@users/components/UserActivation';

export const UserActivationPage: React.FC = () => {
  const { userId, activationToken }: NonNullable<Readonly<Params<string>>> = useParams();

  return (
    <VStack justifyContent="center">
      <Text fontSize="6xl">User activation</Text>
      <UserActivation userId={userId} activationToken={activationToken} />
    </VStack>
  );
};
