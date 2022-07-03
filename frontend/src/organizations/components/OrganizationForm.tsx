import { AxiosError, AxiosResponse } from 'axios';
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
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { CreateOrganizationDto, OrganizationCreatedDto } from '@organizations/dtos';
import { useCreateOrganization } from '@organizations/hooks/api';

const initialValues: CreateOrganizationDto = {
  name: '',
};

export const OrganizationForm: React.FC = () => {
  const { mutate: createOrganization, isSuccess, isLoading } = useCreateOrganization();
  const [isOrganizationCreatedEventReceived, setIsOrganizationCreatedEventReceived] =
    useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleSubmitForm = (dto: CreateOrganizationDto): void =>
    createOrganization(
      { dto },
      { onError: handleCreateOrganizationFailure, onSuccess: handleCreateOrganizationSuccess }
    );

  const { errors, touched, values, handleSubmit, handleChange, setErrors, resetForm } =
    useFormik<CreateOrganizationDto>({
      initialValues,
      onSubmit: handleSubmitForm,
    });

  const handleCreateOrganizationFailure = (
    error: AxiosError<ApplicationErrorDto<any, any>, unknown>
  ): void =>
    applicationErrorHandler<CreateOrganizationDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .handleAxiosError(error);

  const handleCreateOrganizationSuccess = (
    response: AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>
  ): void => {
    setIsOrganizationCreatedEventReceived(false);
    setHandler(
      handler.onOrganizationCreatedEvent(({ data }) => {
        handleOrganizationCreatedEvent();
        return response.data.id === data.organizationId;
      })
    );
  };

  const handleOrganizationCreatedEvent = (): void => {
    setIsOrganizationCreatedEventReceived(true);
    resetForm();
  };
  return (
    <form onSubmit={handleSubmit}>
      <VStack>
        {isSuccess && isOrganizationCreatedEventReceived && (
          <Alert status="success">
            <AlertIcon />
            <AlertDescription>An organization has been created.</AlertDescription>
          </Alert>
        )}
        <FormControl isInvalid={!!errors.name}>
          <FormLabel>Organization name</FormLabel>
          <Input id="name" value={values.name} onChange={handleChange} />
          {errors.name && touched.name && <FormErrorMessage>{errors.name}</FormErrorMessage>}
        </FormControl>
        <Button type="submit" disabled={isLoading}>
          Add
        </Button>
        {isLoading && <Spinner />}
      </VStack>
    </form>
  );
};
