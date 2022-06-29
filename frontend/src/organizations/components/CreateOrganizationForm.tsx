import { Button, FormControl, FormErrorMessage, FormLabel, Input, VStack } from '@chakra-ui/react';
import { useUser } from '@users/contexts';
import { useFormik } from 'formik';
import { CreateOrganizationDto } from 'organizations/dtos/CreateOrganizationDto';
import { useCreateOrganization } from 'organizations/hooks/api';

const initialValues: CreateOrganizationDto = {
  name: '',
};
export const CreateOrganizationForm: React.FC = (): JSX.Element => {
  const { mutate: createOrganization, isLoading } = useCreateOrganization();
  const { jwt } = useUser();
  const handleError = (error: any) => {
    console.log(error);
  };
  const handleSuccess = (message: any) => {
    console.log(message);
  };
  const handleSubmitForm = ({ name }: CreateOrganizationDto) => {
    if (!jwt) return;
    //temporary
    createOrganization({ dto: { name }, jwt }, { onError: handleError, onSuccess: handleSuccess });
  };
  const { errors, touched, values, handleSubmit, handleChange } = useFormik<CreateOrganizationDto>({
    initialValues,
    onSubmit: handleSubmitForm,
  });
  return (
    <form onSubmit={handleSubmit}>
      <VStack>
        <FormControl isInvalid={!!errors.name}>
          <FormLabel>Organization name</FormLabel>
          <Input id="name" value={values.name} onChange={handleChange} />
          {errors.name && touched.name && <FormErrorMessage>{errors.name}</FormErrorMessage>}
        </FormControl>
        <Button type="submit" disabled={isLoading}>
          Add
        </Button>
      </VStack>
    </form>
  );
};
