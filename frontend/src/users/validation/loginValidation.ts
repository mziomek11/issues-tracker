import { object, string } from 'yup';

export const loginValidation = object().shape({
  email: string().required('Required').email('Invalid email'),
  password: string().required('Required'),
});
