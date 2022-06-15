import { FormControl, FormLabel, Input, Button, VStack } from '@chakra-ui/react';
import { FormikProps, useFormik } from 'formik';
import { useNavigate } from 'react-router-dom';

interface FormFields {
  email: string;
  password: string;
}
const initialValues: FormFields = {
  email: '',
  password: '',
};
const handleSubmitForm = (values: FormFields): void => {
  console.log(values);
};
export const LoginForm: React.FC<{}> = (): JSX.Element => {
  const navigate = useNavigate();

  const handleNavigate = (): void => navigate('/register');

  const formik: FormikProps<FormFields> = useFormik<FormFields>({
    initialValues,
    onSubmit: handleSubmitForm,
  });

  return (
    <form onSubmit={formik.handleSubmit}>
      <VStack spacing={1} width="30vw">
        <FormControl>
          <FormLabel htmlFor="email">Email</FormLabel>
          <Input
            id="email"
            type="email"
            autoComplete="off"
            value={formik.values.email}
            onChange={formik.handleChange}
          />
        </FormControl>
        <FormControl>
          <FormLabel htmlFor="password">Password</FormLabel>
          <Input
            id="password"
            type="password"
            value={formik.values.password}
            onChange={formik.handleChange}
          />
        </FormControl>
        <Button size="lg" variant="ghost" type="submit">
          Login
        </Button>
        <Button size="xs" variant="ghost" onClick={handleNavigate}>
          or register
        </Button>
      </VStack>
    </form>
  );
};
