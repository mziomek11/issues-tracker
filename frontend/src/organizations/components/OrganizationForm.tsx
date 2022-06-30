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
import { useRef, useState } from 'react';
import { useSse } from '@notifications/contexts';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { useSseSubscription } from '@notifications/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { useUser } from '@users/contexts';
import { CreateOrganizationDto } from '@organizations/dtos/CreateOrganizationDto';
import { useCreateOrganization } from '@organizations/hooks/api';

const initialValues: CreateOrganizationDto = {
  name: '',
};
export interface OrganizationCreatedDto {
  id: string;
}
export const OrganizationForm: React.FC = () => {
  const { mutate: createOrganization, isSuccess } = useCreateOrganization();
  const [isLoading, setIsLoading] = useState(false);
  const { jwt } = useUser();
  const { sseMessageRef } = useSse();
  const responseMessageRef = useRef<OrganizationCreatedDto>({ id: '' });
  const [handler] = useState(
    sseHandler().onOrganizationCreatedEvent(() => {
      if (responseMessageRef.current.id === sseMessageRef.current.data.organizationId) {
        setIsLoading(false);
        setFieldValue('name', '');
      }
    })
  );
  useSseSubscription(handler);

  const handleSubmitForm = ({ name }: CreateOrganizationDto) => {
    if (!jwt) return;
    setIsLoading(true);
    createOrganization({ dto: { name }, jwt }, { onError: handleError, onSuccess: handleSuccess });
  };
  const { errors, touched, values, handleSubmit, handleChange, setFieldValue, setErrors } =
    useFormik<CreateOrganizationDto>({
      initialValues,
      onSubmit: handleSubmitForm,
    });
  const handleError = (error: AxiosError<ApplicationErrorDto<any, any>, unknown>): void =>
    applicationErrorHandler<CreateOrganizationDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .handleAxiosError(error);

  const handleSuccess = (message: AxiosResponse<OrganizationCreatedDto, CreateOrganizationDto>) => {
    responseMessageRef.current = message.data;
  };
  return (
    <form onSubmit={handleSubmit}>
      <VStack>
        {isSuccess && !isLoading && (
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
