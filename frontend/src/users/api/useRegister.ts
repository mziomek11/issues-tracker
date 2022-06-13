import axios from 'axios'
import { useMutation } from 'react-query'

const postRegister = (data: any) => {
    return axios.post(`http://localhost/api/v1/user-management/users`, data)
}
export const useRegister = () => {
    return useMutation(postRegister)
}   