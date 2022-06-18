import { useNavigate } from 'react-router-dom';
import { FormikProps, useFormik } from 'formik';
import { FormControl, FormLabel, Input, Button, VStack } from '@chakra-ui/react';
import { reverse } from '../../shared/helpers/reverse';
import { LoginFormFields } from '../dtos/login-user.dto';

const initialValues: LoginFormFields = {
  email: '',
  password: '',
};
const handleSubmitForm = (values: LoginFormFields): void => {
  console.log(values);
};
export const LoginForm: React.FC = (): JSX.Element => {
  const navigate = useNavigate();

  const navigateToRegister = (): void => navigate(reverse('users.register'));

  const formik: FormikProps<LoginFormFields> = useFormik<LoginFormFields>({
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
        <Button size="xs" variant="ghost" onClick={navigateToRegister}>
          or register
        </Button>
      </VStack>
    </form>
  );
};
