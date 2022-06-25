import { AxiosError, AxiosResponse } from 'axios';
import { FormControl, FormLabel, Input, Button, VStack, FormErrorMessage } from '@chakra-ui/react';
import { useFormik } from 'formik';
import { Link } from 'react-router-dom';
import { reverse } from '@shared/helpers/routing/reverse';
import { LoginDto } from '@users/dtos';
import { useLogin } from '@users/hooks/api';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { loginValidation } from '@users/validation';
import { JWT } from '@users/consts/localstorage';

const initialValues: LoginDto = {
  email: '',
  password: '',
};

export const LoginForm: React.FC = (): JSX.Element => {
  const { mutate: login } = useLogin();

  const handleSubmitForm = (values: LoginDto): void => {
    login(values, { onError: handleError, onSuccess: handleSuccess });
    setFieldValue('password', '');
  };

  const { errors, touched, values, handleSubmit, handleChange, setFieldError, setFieldValue } =
    useFormik<LoginDto>({
      initialValues,
      onSubmit: handleSubmitForm,
      validationSchema: loginValidation,
    });

  const handleError = (error: AxiosError<ApplicationErrorDto<any, any>, unknown>): void => {
    applicationErrorHandler<LoginDto>()
      .onAuthInvalidCredentials(({ message }) => {
        setFieldError('email', message);
        setFieldError('password', message);
      })
      .handleAxiosError(error);
  };

  const handleSuccess = ({ data: LoggedInUserToken }: AxiosResponse): void => {
    localStorage.setItem(JWT, LoggedInUserToken);
  };
  return (
    <form onSubmit={handleSubmit}>
      <VStack spacing={4} width="30vw">
        <FormControl isInvalid={!!errors.email}>
          <FormLabel htmlFor="email">Email</FormLabel>
          <Input
            id="email"
            type="email"
            autoComplete="off"
            value={values.email}
            onChange={handleChange}
          />
          {errors.email && touched.email && <FormErrorMessage>{errors.email}</FormErrorMessage>}
        </FormControl>
        <FormControl isInvalid={!!errors.password}>
          <FormLabel htmlFor="password">Password</FormLabel>
          <Input id="password" type="password" value={values.password} onChange={handleChange} />
          {errors.password && touched.password && (
            <FormErrorMessage>{errors.password}</FormErrorMessage>
          )}
        </FormControl>
        <Button size="lg" variant="ghost" type="submit">
          Login
        </Button>
        <Link to={reverse('users.register')}>or register</Link>
      </VStack>
    </form>
  );
};
