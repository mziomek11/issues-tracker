import {
  //   Alert,
  //   AlertDescription,
  //   AlertIcon,
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
import { CreateIssueDto } from '@issues/dtos';
import { Type } from '@issues/enums/issues-list';
import { IssuesListParams } from '@issues/types';
import { useOrganizationDetails } from '@organizations/hooks/api';
import { useFormik } from 'formik';
import { FC } from 'react';

interface CreateIssueFormProps extends IssuesListParams {}

const initialValues: CreateIssueDto = {
  type: Type.BUG,
  name: '',
  content: '',
};

export const CreateIssueForm: FC<CreateIssueFormProps> = (params) => {
  const handleSubmitForm = () => {};

  const { handleSubmit, handleChange, errors, values, touched } = useFormik<CreateIssueDto>({
    initialValues,
    onSubmit: handleSubmitForm,
  });

  const { data: projectData, isFetching } = useOrganizationDetails(params.organizationId, {});

  if (isFetching) return <Spinner />;

  return (
    <Stack alignItems={'center'}>
      <Text fontSize="4xl">
        {projectData?.data.name} / {projectData?.data.projects.find((project) => project.id === params.projectId)?.name}
      </Text>
      <form onSubmit={handleSubmit}>
        <VStack>
          {/* {isSuccess && isOrganizationCreatedEventReceived && (
            <Alert status="success">
              <AlertIcon />
              <AlertDescription>An organization has been created.</AlertDescription>
            </Alert>
          )} */}
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
          <Button type="submit">Add</Button>
          {/* {isLoading && <Spinner />} */}
        </VStack>
      </form>
    </Stack>
  );
};
