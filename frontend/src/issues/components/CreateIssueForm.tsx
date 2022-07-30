import { AxiosError, AxiosResponse } from 'axios';
import {
  Button,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Input,
  Select,
  Spinner,
  Stack,
  Text,
  Textarea,
  VStack,
} from '@chakra-ui/react';
import { useFormik } from 'formik';
import { FC, useState } from 'react';
import { CreateIssueDto, IssueCreatedDto } from '@issues/dtos';
import { Type } from '@issues/enums/issues-list';
import { useCreateIssue } from '@issues/hooks';
import { IssuesListParams } from '@issues/types';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useOrganizationDetails } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';

interface CreateIssueFormProps extends IssuesListParams {}

const initialValues: CreateIssueDto = {
  type: Type.BUG,
  name: '',
  content: '',
};

export const CreateIssueForm: FC<CreateIssueFormProps> = (params) => {
  const [error, setError] = useState('');
  const [handler, setHandler] = useState(sseHandler());
  const [isIssueCreatedEventRecived, setIsIssueCreatedEventRecived] = useState(false);

  const handleSuccess = (response: AxiosResponse<IssueCreatedDto, CreateIssueDto>) => {
    setIsIssueCreatedEventRecived(false);
    setHandler(
      handler.onIssueOpenedEvent(({ data }) => {
        const organizationValidation = data.organizationId === params.organizationId;
        const projectValidation = data.projectId === params.projectId;
        const issueValidation = data.issueId === response.data.id;
        // const memberValidation = data.memberId ===
        if (organizationValidation && projectValidation && issueValidation)
          setIsIssueCreatedEventRecived(true);
      })
    );
  };

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => {
    applicationErrorHandler<CreateIssueDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .onAuthInvalidJwt(({ message }) => setError(message))
      .onOrganizationAccessDenied(({ message }) => setError(message))
      .onOrganizationNotFound(({ message }) => setError(message))
      .onOrganizationProjectNotFound(({ message }) => setError(message))
      .handleAxiosError(error);
  };

  const { mutate: createIssue, isLoading } = useCreateIssue(params, {
    onSuccess: handleSuccess,
    onError: handleError,
  });

  const handleSubmitForm = (values: CreateIssueDto) => {
    createIssue(values);
  };

  const { handleSubmit, handleChange, setErrors, errors, values, touched } =
    useFormik<CreateIssueDto>({
      initialValues,
      onSubmit: handleSubmitForm,
    });

  const { data: projectData, isFetching } = useOrganizationDetails(params.organizationId, {});

  if (isFetching) return <Spinner />;

  return (
    <Stack alignItems={'center'}>
      <Text fontSize="4xl">
        {projectData?.data.name} /{' '}
        {projectData?.data.projects.find((project) => project.id === params.projectId)?.name}
      </Text>
      <form onSubmit={handleSubmit}>
        <VStack>
          <FormControl isInvalid={!!errors.type}>
            <FormLabel>Issue type</FormLabel>
            <Select id="type" value={values.type} onChange={handleChange}>
              <option value={Type.BUG}>BUG</option>
              <option value={Type.ENHANCEMENT}>ENHANCEMENT</option>
            </Select>
          </FormControl>
          <FormControl isInvalid={!!errors.name}>
            <FormLabel>Issue name</FormLabel>
            <Input id="name" value={values.name} onChange={handleChange} />
            {errors.name && touched.name && <FormErrorMessage>{errors.name}</FormErrorMessage>}
          </FormControl>
          <FormControl>
            <FormLabel>Issue content</FormLabel>
            <Textarea id="content" value={values.content} onChange={handleChange} />
          </FormControl>
          <Button type="submit" disabled={isLoading}>
            Add
          </Button>
        </VStack>
      </form>
    </Stack>
  );
};
