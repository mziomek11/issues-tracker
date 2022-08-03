import { AxiosError, AxiosResponse } from 'axios';
import {
  FormControl,
  FormLabel,
  Input,
  Button,
  FormErrorMessage,
  Link as ChakraLink,
} from '@chakra-ui/react';
import { useFormik } from 'formik';
import { Link } from 'react-router-dom';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { reverse } from '@shared/helpers/routing/reverse';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { useUser } from '@users/contexts';
import { LoginDto } from '@users/dtos';
import { useLogin } from '@users/hooks/api';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { FormActions, FormFields } from '@shared/components';

const initialValues: LoginDto = {
  email: '',
  password: '',
};

export const LoginForm: React.FC = (): JSX.Element => {
  const { mutate: login, isLoading } = useLogin();
  const { loginUser } = useUser();
  const handleSubmitForm = (values: LoginDto): void => {
    login(values, { onError: handleError, onSuccess: handleSuccess });
    setFieldValue('password', '');
  };

  const { errors, values, handleSubmit, handleChange, setFieldError, setFieldValue } =
    useFormik<LoginDto>({
      initialValues,
      onSubmit: handleSubmitForm,
    });

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler<LoginDto>()
      .onAuthInvalidCredentials(({ message }) => {
        setFieldError('email', message);
        setFieldError('password', message);
      })
      .handleAxiosError(error);
  };

  const handleSuccess = ({ data: loggedInUserToken }: AxiosResponse): void => {
    loginUser(loggedInUserToken);
  };
  return (
    <form onSubmit={handleSubmit}>
      <FormFields>
        <FormControl isInvalid={!!errors.email}>
          <FormLabel htmlFor="email">Email</FormLabel>
          <Input
            id="email"
            type="email"
            autoComplete="off"
            value={values.email}
            onChange={handleChange}
          />
          <FormErrorMessage>{errors.email}</FormErrorMessage>
        </FormControl>
        <FormControl isInvalid={!!errors.password}>
          <FormLabel htmlFor="password">Password</FormLabel>
          <Input id="password" type="password" value={values.password} onChange={handleChange} />
          <FormErrorMessage>{errors.password}</FormErrorMessage>
        </FormControl>
      </FormFields>

      <FormActions>
        <Button size="lg" type="submit" isLoading={isLoading}>
          Login
        </Button>

        <ChakraLink as={Link} to={reverse('users.register')} textDecoration="underline">
          or register
        </ChakraLink>
      </FormActions>
    </form>
  );
};
