import {
    FormControl,
    FormLabel,
    Input,
    Button,
    VStack,
  } from '@chakra-ui/react'
import { FormikProps, useFormik } from 'formik'
import { useNavigate } from 'react-router-dom';
import { useRegister } from '../api/useRegister';

interface FormFields {
        email: string;
        password: string;
        repeatPassword: string;
}
const initialValues: FormFields = {
    email: '',
    password: '',
    repeatPassword: '',
}
export const RegisterForm: React.FC<{}> = () => {
    const navigate = useNavigate();
    const handleNavigate = () => navigate('/login');
    const { mutate } = useRegister();
    const handleSubmitForm = (values: FormFields) => {
        const crudentials = {email: values.email, password: values.password}
        mutate(crudentials)
    }
    const formik:FormikProps<FormFields> = useFormik<FormFields>({
        initialValues,
        onSubmit: handleSubmitForm
    })

    return(
        <form
            onSubmit={formik.handleSubmit}
        >
        <VStack
            spacing={1}
            width='30vw'
        >
            <FormControl>
                <FormLabel htmlFor='email'>Email</FormLabel>
                <Input id='email' type='email' autoComplete='off' value={formik.values.email} onChange={formik.handleChange}/>
            </FormControl>
            <FormControl>
                <FormLabel htmlFor='password'>Password</FormLabel>
                <Input id='password' type='password' value={formik.values.password} onChange={formik.handleChange}/>
            </FormControl>
            <FormControl>
                <FormLabel htmlFor='repeatPassword'>Repeat password</FormLabel>
                <Input id='repeatPassword' type='password' value={formik.values.repeatPassword} onChange={formik.handleChange}/>
            </FormControl>
            <Button size='lg' variant='ghost' type='submit'>
                Register
            </Button>
            <Button size='xs' variant='ghost' onClick={handleNavigate}>
                or login
            </Button>
        </VStack>
        </form>
    )
}