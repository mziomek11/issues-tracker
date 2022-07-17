import { AxiosError } from 'axios';
import { Badge, Spinner, Text, VStack } from '@chakra-ui/react';
import { validate as uuidValidate } from 'uuid';
import { useEffect, useState } from 'react';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useAcceptInviteMember } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { useSseSubscription } from '@notifications/hooks/api';

interface MemberAcceptanceProps {
  organizationId: string;
}

export const MemberAcceptance: React.FC<MemberAcceptanceProps> = ({ organizationId }) => {
  const { mutate: acceptInvite, isError, isLoading, isSuccess } = useAcceptInviteMember();
  const isUuidValid = uuidValidate(organizationId);
  const [errorMessage, setErrorMessage] = useState('');
  const [isMemberJoinedEventRecived, setIsMemberJoinedEventRecived] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleAcceptanceFailure = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler()
      .onAuthInvalidJwt(({ message }) => setErrorMessage(message))
      .onOrganizationInvitationNotFound(({ message }) => setErrorMessage(message))
      .onOrganizationNotFound(({ message }) => setErrorMessage(message))
      .handleAxiosError(error);
  };
  const handleAcceptanceSuccess = (): void => {
    setHandler(
      handler.onOrganizationMemberJoinedEvent(({ data }) => {
        if (data.organizationId === organizationId) setIsMemberJoinedEventRecived(true);
      })
    );
  };
  const handleAcceptance = (): void => {
    if (isUuidValid)
      acceptInvite(organizationId, {
        onError: handleAcceptanceFailure,
        onSuccess: handleAcceptanceSuccess,
      });
  };
  useEffect(() => {
    handleAcceptance();
  }, []);
  return (
    <VStack>
      {isLoading && <Spinner />}
      {isError && (
        <>
          <Badge colorScheme="red">Error</Badge>
          <Text fontSize="md">{errorMessage}</Text>
        </>
      )}
      {isSuccess && isMemberJoinedEventRecived && (
        <>
          <Badge colorScheme="green">Joined</Badge>
          <Text fontSize="md">User joined</Text>
        </>
      )}
      {!isUuidValid && (
        <>
          <Badge colorScheme="yellow">Warning</Badge>
          <Text fontSize="md">This is not valid organization uuid</Text>
        </>
      )}
    </VStack>
  );
};
