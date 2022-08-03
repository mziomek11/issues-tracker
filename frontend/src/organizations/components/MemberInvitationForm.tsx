import { AxiosError } from 'axios';
import {
  Button,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Input,
  useToast,
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
import { FormActions, FormFields } from '@shared/components';
import { useNavigate } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';

const initialValues: MemberInvitationDto = {
  email: '',
};
interface MemberInvitationFormProps extends OrganizationParams {}

export const MemberInvitationForm: React.FC<MemberInvitationFormProps> = ({ organizationId }) => {
  const toast = useToast();
  const navigate = useNavigate();
  const { mutate: inviteMember, isLoading } = useInviteMember(organizationId);
  const [isWaitingForMemberInvitedEvent, setIsWaitingForMemberInvitedEvent] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleSubmitForm = (dto: MemberInvitationDto): void => {
    inviteMember(dto, { onError: handleInviteMemberFailure, onSuccess: handleInviteMemberSuccess });
  };
  const { errors, values, handleSubmit, handleChange, setFieldError, setErrors } =
    useFormik<MemberInvitationDto>({
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
    setIsWaitingForMemberInvitedEvent(true);

    setHandler(
      handler.onOrganizationMemberInvitedEvent(({ data }) => {
        if (data.organizationId === organizationId) {
          toast({ status: 'success', title: 'Member has been successfully invited' });
          navigate(reverse({ path: 'organizations.details', params: { organizationId } }));
        }
      })
    );
  };

  return (
    <form onSubmit={handleSubmit}>
      <FormFields>
        <FormControl isInvalid={!!errors.email}>
          <FormLabel htmlFor="email">Member email</FormLabel>
          <Input id="email" name="email" value={values.email} onChange={handleChange} />
          <FormErrorMessage>{errors.email}</FormErrorMessage>
        </FormControl>
      </FormFields>

      <FormActions>
        <Button type="submit" isLoading={isLoading || isWaitingForMemberInvitedEvent}>
          Invite member
        </Button>
      </FormActions>
    </form>
  );
};
