import { object, string, ref } from 'yup';

export const registerValidation = object().shape({
  email: string().required('Required').email('Invalid email'),
  password: string().required('Required'),
  repeatPassword: string()
    .required('Required')
    .oneOf([ref('password')], 'Passwords do not match'),
});
