import { AxiosError, AxiosResponse } from 'axios';
import { Button, FormControl, FormErrorMessage, FormLabel, Input } from '@chakra-ui/react';
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
import { FormActions, FormFields } from '@shared/components';
import { useNavigate } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';

const initialValues: CreateProjectDto = {
  name: '',
};
interface ProjectFormParms {
  organizationId: string;
}
export const ProjectForm: React.FC<ProjectFormParms> = ({ organizationId }) => {
  const navigate = useNavigate();
  const { mutate: createProject, isLoading } = useCreateProject();
  const [isWaitingForProjectCreatedEvent, setIsWaitingForProjectCreatedEvent] = useState(false);
  const [handler, setHandler] = useState(sseHandler());
  useSseSubscription(handler);

  const handleSubmitForm = (dto: CreateProjectDto): void => {
    createProject(
      { dto, organizationId },
      { onError: handleCreateProjectFailure, onSuccess: handleCreateProjectSuccess }
    );
  };
  const { errors, values, handleSubmit, handleChange, setFieldError, setErrors } =
    useFormik<CreateProjectDto>({
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
    setIsWaitingForProjectCreatedEvent(true);
    setHandler(
      handler.onOrganizationProjectCreatedEvent(({ data }) => {
        if (response.data.id === data.projectId) {
          navigate(reverse({ path: 'organizations.details', params: { organizationId } }));
        }
      })
    );
  };

  return (
    <form onSubmit={handleSubmit}>
      <FormFields>
        <FormControl isInvalid={!!errors.name}>
          <FormLabel>Project name</FormLabel>
          <Input id="name" value={values.name} onChange={handleChange} />
          <FormErrorMessage>{errors.name}</FormErrorMessage>
        </FormControl>
      </FormFields>

      <FormActions>
        <Button type="submit" isLoading={isLoading || isWaitingForProjectCreatedEvent}>
          Create
        </Button>
      </FormActions>
    </form>
  );
};
