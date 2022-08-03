import { useState } from 'react';
import { AxiosError } from 'axios';
import { ListItem, Text, Button, HStack, useToast } from '@chakra-ui/react';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useAcceptInviteMember } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { useSseSubscription } from '@notifications/hooks/api';
import { ListInvitationDto } from '@organizations/dtos';

interface MemberAcceptanceItemProps {
  invitation: ListInvitationDto;
}

export const MemberAcceptanceItem: React.FC<MemberAcceptanceItemProps> = ({ invitation }) => {
  const toast = useToast();
  const { mutate: acceptInvite, isLoading } = useAcceptInviteMember();
  const [isWaitingForMemberJoinedEvent, setIssWaitingForMemberJoinedEvent] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleAcceptanceFailure = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler()
      .onDefault(({ message }) => toast({ status: 'error', title: message }))
      .handleAxiosError(error);
  };

  const handleAcceptanceSuccess = (): void => {
    setIssWaitingForMemberJoinedEvent(true);

    setHandler(
      handler.onOrganizationMemberJoinedEvent(({ data }) => {
        if (data.organizationId === invitation.organization.id) {
          toast({ status: 'success', title: 'You successfully joined organization' });
        }
      })
    );
  };

  const handleJoin = (): void => {
    acceptInvite(invitation.organization.id, {
      onError: handleAcceptanceFailure,
      onSuccess: handleAcceptanceSuccess,
    });
  };

  return (
    <ListItem>
      <HStack>
        <Text>{invitation.organization.name}</Text>
        <Button onClick={handleJoin} isLoading={isLoading || isWaitingForMemberJoinedEvent}>
          Join
        </Button>
      </HStack>
    </ListItem>
  );
};
