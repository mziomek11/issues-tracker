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
  Select,
  Box,
  Textarea,
} from '@chakra-ui/react';
import { useFormik } from 'formik';
import { FC, useState } from 'react';
import { CreateIssueDto, IssueCreatedDto } from '@issues/dtos';
import { IssueType } from '@issues/enums';
import { useCreateIssue } from '@issues/hooks';
import { IssuesListParams } from '@issues/types';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { useNavigate } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing';
import { useSseSubscription } from '@notifications/hooks/api';
import { FormActions, FormFields } from '@shared/components';

interface CreateIssueFormProps extends IssuesListParams {}

const initialValues: CreateIssueDto = {
  type: IssueType.BUG,
  name: '',
  content: '',
};

export const CreateIssueForm: FC<CreateIssueFormProps> = (params) => {
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [handler, setHandler] = useState(sseHandler());
  const [isWaitingForProjectCreatedEvent, setIsWaitingForProjectCreatedEvent] = useState(false);
  useSseSubscription(handler);

  const handleSuccess = (response: AxiosResponse<IssueCreatedDto, CreateIssueDto>): void => {
    setIsWaitingForProjectCreatedEvent(true);
    setHandler(
      handler.onIssueOpenedEvent(({ data }) => {
        const organizationValidation = data.organizationId === params.organizationId;
        const projectValidation = data.projectId === params.projectId;
        const issueValidation = data.issueId === response.data.id;
        if (organizationValidation && projectValidation && issueValidation)
          navigate(reverse({ path: 'issues.list', params }));
      })
    );
  };

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler<CreateIssueDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .onAuthInvalidJwt(({ message }) => setError(message))
      .onOrganizationAccessDenied(({ message }) => setError(message))
      .onOrganizationNotFound(({ message }) => setError(message))
      .onOrganizationProjectNotFound(({ message }) => setError(message))
      .handleAxiosError(error);
  };

  const {
    mutate: createIssue,
    isLoading,
    isError,
  } = useCreateIssue(params, {
    onSuccess: handleSuccess,
    onError: handleError,
  });

  const handleSubmitForm = (values: CreateIssueDto): void => {
    createIssue(values);
  };

  const { handleSubmit, handleChange, setErrors, errors, values } = useFormik<CreateIssueDto>({
    initialValues,
    onSubmit: handleSubmitForm,
  });

  return (
    <Box>
      {isError && error && (
        <Alert status="error">
          <AlertIcon />
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}
      <form onSubmit={handleSubmit}>
        <FormFields>
          <FormControl isInvalid={!!errors.type}>
            <FormLabel>Issue type</FormLabel>
            <Select id="type" value={values.type} onChange={handleChange}>
              <option value={IssueType.BUG}>{IssueType.BUG}</option>
              <option value={IssueType.ENHANCEMENT}>{IssueType.ENHANCEMENT}</option>
            </Select>
          </FormControl>
          <FormControl isInvalid={!!errors.name}>
            <FormLabel>Issue name</FormLabel>
            <Input name="name" id="name" value={values.name} onChange={handleChange} />
            <FormErrorMessage>{errors.name}</FormErrorMessage>
          </FormControl>
          <FormControl isInvalid={!!errors.content}>
            <FormLabel>Issue content</FormLabel>
            <Textarea name="content" id="content" value={values.content} onChange={handleChange} />
            <FormErrorMessage>{errors.content}</FormErrorMessage>
          </FormControl>
        </FormFields>

        <FormActions>
          <Button type="submit" isLoading={isLoading || isWaitingForProjectCreatedEvent}>
            Open issue
          </Button>
        </FormActions>
      </form>
    </Box>
  );
};
