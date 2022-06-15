import {
    FormControl,
    FormLabel,
    Input,
    Button,
    VStack,
    Text
  } from '@chakra-ui/react'
import { FormikProps, useFormik } from 'formik'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GenericServerError, useRegister } from '../api/useRegister';

interface FormFields {
        email: string;
        password: string;
        repeatPassword: string;
}
export interface ErrorCode {
    code: GenericServerError | null,
    message: string
}

const initialValues: FormFields = {
    email: '',
    password: '',
    repeatPassword: '',
}
export const RegisterForm: React.FC<{}> = () => {
    const navigate = useNavigate();
    const [errorCode, setErrorCode] = useState<ErrorCode>({code: null, message: ''})
    const { mutate, error } = useRegister(setErrorCode);
    const handleNavigate = () => navigate('/login');
    
    const handleSubmitForm = (values: FormFields) => {
        const credentials = {email: values.email, password: values.password}
        if(values.password === values.repeatPassword){
            mutate(credentials)
        } else {
            console.error('Passwords dont match')
        }
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
                <Input id='email' type='email' autoComplete='off' value={formik.values.email} onChange={formik.handleChange} required/>
                {error && errorCode.code === GenericServerError.GENERIC_3 && <Text fontSize='sm' color="red">{errorCode.message}</Text>}
            </FormControl>
            <FormControl>
                <FormLabel htmlFor='password'>Password</FormLabel>
                <Input id='password' type='password' value={formik.values.password} onChange={formik.handleChange} required/>
            </FormControl>
            <FormControl>
                <FormLabel htmlFor='repeatPassword'>Repeat password</FormLabel>
                <Input id='repeatPassword' type='password' value={formik.values.repeatPassword} onChange={formik.handleChange} required/>
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