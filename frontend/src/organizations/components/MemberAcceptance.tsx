import { Spinner, UnorderedList, VStack, Text } from '@chakra-ui/react';
import { useInvitations } from '@organizations/hooks/api/useInvitations';
import { MemberAcceptanceItem } from './MemberAcceptanceItem';

export const MemberAcceptance: React.FC = () => {
  const { data: invitations, isSuccess, isLoading } = useInvitations();

  return (
    <VStack>
      {isLoading && <Spinner />}
      {isSuccess && (
        <>
          {invitations.data.length === 0 && <Text>You do not have any invitation</Text>}
          <UnorderedList>
            {invitations.data.map((invitation) => (
              <MemberAcceptanceItem key={invitation.organization.id} invitation={invitation} />
            ))}
          </UnorderedList>
        </>
      )}
    </VStack>
  );
};
