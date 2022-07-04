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
import { CreateProjectDto, ProjectCreatedDto } from '@organizations/dtos';
import { useCreateProject } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorCode } from '@shared/enums/error-code';

const initialValues: CreateProjectDto = {
  name: '',
};
interface ProjectFormParms {
  organizationId: string;
}
export const ProjectForm: React.FC<ProjectFormParms> = ({ organizationId }) => {
  const { mutate: createProject, isSuccess, isLoading } = useCreateProject();
  const [isProjectCreatedEventReceived, setIsProjectCreatedEventReceived] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleSubmitForm = (dto: CreateProjectDto): void => {
    createProject(
      { dto, organizationId },
      { onError: handleCreateProjectFailure, onSuccess: handleCreateProjectSuccess }
    );
  };
  const {
    errors,
    touched,
    values,
    handleSubmit,
    handleChange,
    setFieldError,
    resetForm,
    setErrors,
  } = useFormik<CreateProjectDto>({
    initialValues,
    onSubmit: handleSubmitForm,
  });

  const handleCreateProjectFailure = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler<CreateProjectDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .onOrganizationNotFound(({ message }) => setFieldError('name', message))
      .onOrganizationOwnerInvalid(({ message }) => setFieldError('name', message))
      .handleAxiosError(error);
  };

  const handleCreateProjectSuccess = (
    response: AxiosResponse<ProjectCreatedDto, CreateProjectDto>
  ): void => {
    setIsProjectCreatedEventReceived(false);
    setHandler(
      handler.onOrganizationProjectCreatedEvent(({ data }) => {
        handleProjectCreatedEvent();
        return response.data.id === data.projectId;
      })
    );
  };

  const handleProjectCreatedEvent = (): void => {
    setIsProjectCreatedEventReceived(true);
    resetForm();
  };

  return (
    <form onSubmit={handleSubmit}>
      <VStack>
        {isSuccess && isProjectCreatedEventReceived && (
          <Alert status="success">
            <AlertIcon />
            <AlertDescription>A project has been created.</AlertDescription>
          </Alert>
        )}
        <FormControl isInvalid={!!errors.name}>
          <FormLabel>Project name</FormLabel>
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
