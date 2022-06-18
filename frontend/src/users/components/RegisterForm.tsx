import { useNavigate } from 'react-router-dom';
import { FormControl, FormLabel, Input, Button, VStack, Text } from '@chakra-ui/react';
import { FormikProps, useFormik } from 'formik';
import { object, string, ref } from 'yup';
import { reverse } from '../../shared/helpers/reverse';
import { ApplicationErrorCode, applicationErrors } from '../../shared/dtos/application-error.dto';
import { useRegister } from '../hooks/api/useRegister';
// import { onSuccessRegister } from '../hooks/api/onSuccessRegister';
import { RegisterFormFields, ResponseDataDto } from '../dtos/register-user.dto';
import { AxiosError } from 'axios';

const initialValues: RegisterFormFields = {
  email: '',
  password: '',
  repeatPassword: '',
};

const ValidationSchema = object().shape({
  email: string().required('Required').email('Invalid email'),
  password: string().required('Required'),
  repeatPassword: string()
    .required('Required')
    .oneOf([ref('password')], 'Passwords do not match'),
});

export const RegisterForm: React.FC = () => {
  const navigate = useNavigate();
  const { mutate } = useRegister();
  const navigateToLogin = (): void => navigate(reverse('users.login'));

  const handleSubmitForm = (values: RegisterFormFields): void => {
    const registerUser = { email: values.email, password: values.password };
    mutate(registerUser, { onError });
  };
  const formik: FormikProps<RegisterFormFields> = useFormik<RegisterFormFields>({
    initialValues,
    onSubmit: handleSubmitForm,
    validationSchema: ValidationSchema,
  });
  const onError = (error: AxiosError<ResponseDataDto, unknown>): void => {
    if (error?.response?.data.code === ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE) {
      formik.setFieldError(
        'email',
        applicationErrors[ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE]
      );
    }
  };

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
          {(formik.errors.email && formik.touched.email) && (
            <Text fontSize="sm" color="red">
              {formik.errors.email}
            </Text>
          )}
        </FormControl>
        <FormControl>
          <FormLabel htmlFor="password">Password</FormLabel>
          <Input
            id="password"
            type="password"
            value={formik.values.password}
            onChange={formik.handleChange}
          />
          {formik.errors.password && formik.touched.password && (
            <Text fontSize="sm" color="red">
              {formik.errors.password}
            </Text>
          )}
        </FormControl>
        <FormControl>
          <FormLabel htmlFor="repeatPassword">Repeat password</FormLabel>
          <Input
            id="repeatPassword"
            type="password"
            value={formik.values.repeatPassword}
            onChange={formik.handleChange}
          />
          {formik.errors.repeatPassword && formik.touched.repeatPassword && (
            <Text fontSize="sm" color="red">
              {formik.errors.repeatPassword}
            </Text>
          )}
        </FormControl>
        <Button size="lg" variant="ghost" type="submit">
          Register
        </Button>
        <Button size="xs" variant="ghost" onClick={navigateToLogin}>
          or login
        </Button>
      </VStack>
    </form>
  );
};
