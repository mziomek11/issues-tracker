import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';
import { useFormik } from 'formik';
import { FormControl, FormLabel, FormErrorMessage, Input, Button, VStack } from '@chakra-ui/react';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { reverse } from '@shared/helpers/routing/reverse';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { useRegister } from '@users/hooks/api/useRegister';
import { RegisterUserDto } from '@users/dtos';
import { registerValidation } from '@users/validation';

interface RegisterFormValues {
  email: string;
  password: string;
  repeatPassword: string;
}

const initialValues: RegisterFormValues = {
  email: '',
  password: '',
  repeatPassword: '',
};

export const RegisterForm: React.FC = () => {
  const navigate = useNavigate();
  const { mutate: register } = useRegister();

  const navigateToLogin = (): void => navigate(reverse('users.login'));

  const handleSubmitForm = (values: RegisterFormValues): void =>
    register({ email: values.email, password: values.password }, { onError: handleError });

  const { errors, touched, values, handleChange, handleSubmit, setErrors, setFieldError } =
    useFormik<RegisterFormValues>({
      initialValues,
      onSubmit: handleSubmitForm,
      validationSchema: registerValidation,
    });

  const handleError = (error: AxiosError<ApplicationErrorDto<any, any>, unknown>): void =>
    applicationErrorHandler<RegisterUserDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .onGenericEmailUnavailable(({ message }) => setFieldError('email', message))
      .handleAxiosError(error);

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

        <FormControl isInvalid={!!errors.repeatPassword}>
          <FormLabel htmlFor="repeatPassword">Repeat password</FormLabel>
          <Input
            id="repeatPassword"
            type="password"
            value={values.repeatPassword}
            onChange={handleChange}
          />
          {errors.repeatPassword && touched.repeatPassword && (
            <FormErrorMessage>{errors.repeatPassword}</FormErrorMessage>
          )}
        </FormControl>

        <Button size="lg" type="submit">
          Register
        </Button>
        <Button size="xs" variant="ghost" onClick={navigateToLogin}>
          or login
        </Button>
      </VStack>
    </form>
  );
};
