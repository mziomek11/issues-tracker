import { AxiosError } from 'axios';
import {
  FormControl,
  FormLabel,
  FormErrorMessage,
  Input,
  Button,
  Alert,
  AlertIcon,
  AlertDescription,
  Link as ChakraLink,
} from '@chakra-ui/react';
import { useFormik } from 'formik';
import { Link } from 'react-router-dom';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { reverse } from '@shared/helpers/routing/reverse';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { useRegister } from '@users/hooks/api/useRegister';
import { RegisterUserDto } from '@users/dtos';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { FormActions, FormFields } from '@shared/components';

interface RegisterFormValues {
  email: string;
  password: string;
}

const initialValues: RegisterFormValues = {
  email: '',
  password: '',
};

export const RegisterForm: React.FC = () => {
  const { mutate: register, isSuccess, isLoading } = useRegister();

  const handleSubmitForm = (values: RegisterFormValues): void =>
    register(
      { email: values.email, password: values.password },
      { onError: handleError, onSuccess: handleSuccess }
    );

  const { errors, values, handleChange, handleSubmit, setErrors, setFieldError, resetForm } =
    useFormik<RegisterFormValues>({
      initialValues,
      onSubmit: handleSubmitForm,
    });

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void =>
    applicationErrorHandler<RegisterUserDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .onGenericEmailUnavailable(({ message }) => setFieldError('email', message))
      .handleAxiosError(error);

  const handleSuccess = (): void => {
    resetForm();
  };

  return (
    <form onSubmit={handleSubmit}>
      {isSuccess && (
        <Alert status="success" mb="4">
          <AlertIcon />
          <AlertDescription>
            Account has been successfully created. Now you can&nbsp;
            <ChakraLink as={Link} to={reverse('users.login')} textDecoration="underline">
              log in
            </ChakraLink>
          </AlertDescription>
        </Alert>
      )}

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
          Register
        </Button>

        <ChakraLink as={Link} to={reverse('users.login')} textDecoration="underline">
          or login
        </ChakraLink>
      </FormActions>
    </form>
  );
};
