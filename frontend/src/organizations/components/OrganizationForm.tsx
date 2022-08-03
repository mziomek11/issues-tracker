import { AxiosError, AxiosResponse } from 'axios';
import { Button, FormControl, FormErrorMessage, FormLabel, Input } from '@chakra-ui/react';
import { useFormik } from 'formik';
import { useState } from 'react';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { CreateOrganizationDto, OrganizationCreatedDto } from '@organizations/dtos';
import { useCreateOrganization } from '@organizations/hooks/api';
import { FormActions, FormFields } from '@shared/components';
import { useNavigate } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';

const initialValues: CreateOrganizationDto = {
  name: '',
};

export const OrganizationForm: React.FC = () => {
  const navigate = useNavigate();
  const { mutate: createOrganization, isLoading } = useCreateOrganization();
  const [isWaitingForOrganizationCreatedEvent, setIsWaitingForOrganizationCreatedEvent] =
    useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleSubmitForm = (dto: CreateOrganizationDto): void =>
    createOrganization(
      { dto },
      { onError: handleCreateOrganizationFailure, onSuccess: handleCreateOrganizationSuccess }
    );

  const { errors, values, handleSubmit, handleChange, setErrors } =
    useFormik<CreateOrganizationDto>({
      initialValues,
      onSubmit: handleSubmitForm,
    });

  const handleCreateOrganizationFailure = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void =>
    applicationErrorHandler<CreateOrganizationDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .handleAxiosError(error);

  const handleCreateOrganizationSuccess = (
    response: AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>
  ): void => {
    setIsWaitingForOrganizationCreatedEvent(true);
    setHandler(
      handler.onOrganizationCreatedEvent(({ data }) => {
        if (response.data.id === data.organizationId) {
          navigate(
            reverse({ path: 'organizations.details', params: { organizationId: response.data.id } })
          );
        }
      })
    );
  };

  return (
    <form onSubmit={handleSubmit}>
      <FormFields>
        <FormControl isInvalid={!!errors.name}>
          <FormLabel htmlFor="name">Organization name</FormLabel>
          <Input id="name" name="name" value={values.name} onChange={handleChange} />
          <FormErrorMessage>{errors.name}</FormErrorMessage>
        </FormControl>
      </FormFields>

      <FormActions>
        <Button type="submit" isLoading={isLoading || isWaitingForOrganizationCreatedEvent}>
          Create
        </Button>
      </FormActions>
    </form>
  );
};
