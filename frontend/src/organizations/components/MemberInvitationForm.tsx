import { AxiosError } from 'axios';
import {
  Alert,
  AlertDescription,
  AlertIcon,
  Button,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Input,
  Spinner,
  VStack,
} from '@chakra-ui/react';
import { useFormik } from 'formik';
import { useState } from 'react';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { MemberInvitationDto } from '@organizations/dtos';
import { useInviteMember } from '@organizations/hooks/api';
import { OrganizationParams } from '@organizations/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';

const initialValues: MemberInvitationDto = {
  email: '',
};
interface MemberInvitationFormProps extends OrganizationParams {}

export const MemberInvitationForm: React.FC<MemberInvitationFormProps> = ({ organizationId }) => {
  const { mutate: inviteMember, isSuccess, isLoading } = useInviteMember(organizationId);
  const [isMemberInvitedEventReceived, setIsMemberInvitedEventReceived] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleSubmitForm = (dto: MemberInvitationDto): void => {
    inviteMember(dto, { onError: handleInviteMemberFailure, onSuccess: handleInviteMemberSuccess });
  };
  const {
    errors,
    touched,
    values,
    handleSubmit,
    handleChange,
    setFieldError,
    setErrors,
    resetForm,
  } = useFormik<MemberInvitationDto>({
    initialValues,
    onSubmit: handleSubmitForm,
  });

  const handleInviteMemberFailure = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler<MemberInvitationDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .onAuthInvalidJwt(({ message }) => setFieldError('email', message))
      .onUserNotFound(({ message }) => setFieldError('email', message))
      .onOrganizationOwnerInvalid(({ message }) => setFieldError('email', message))
      .onOrganizationNotFound(({ message }) => setFieldError('email', message))
      .onOrganizationInvitationAlreadyPresent(({ message }) => setFieldError('email', message))
      .onOrganizationMemberAlreadyPresent(({ message }) => setFieldError('email', message))
      .handleAxiosError(error);
  };
  const handleInviteMemberSuccess = (): void => {
    setHandler(
      handler.onOrganizationMemberInvitedEvent(({ data }) => {
        if (data.organizationId === organizationId) handleOrganizationMemberInvitedEvent();
      })
    );
  };
  const handleOrganizationMemberInvitedEvent = (): void => {
    setIsMemberInvitedEventReceived(true);
    resetForm();
  };
  return (
    <form onSubmit={handleSubmit}>
      <VStack>
        {isSuccess && isMemberInvitedEventReceived && (
          <Alert status="success">
            <AlertIcon />
            <AlertDescription>An user has been invited.</AlertDescription>
          </Alert>
        )}
        <FormControl isInvalid={!!errors.email}>
          <FormLabel>User email</FormLabel>
          <Input id="email" value={values.email} onChange={handleChange} />
          {errors.email && touched.email && <FormErrorMessage>{errors.email}</FormErrorMessage>}
        </FormControl>
        <Button type="submit" disabled={isLoading}>
          Invite
        </Button>
        {isLoading && <Spinner />}
      </VStack>
    </form>
  );
};
