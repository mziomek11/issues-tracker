import axios, { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { ErrorCode } from 'users/components/RegisterForm';

interface Credentials {
  email: string;
  password: string;
}

export enum GenericServerError {
  GENERIC_1 = 'GENERIC_1',
  GENERIC_2 = 'GENERIC_2',
  GENERIC_3 = 'GENERIC_3',
}
export const useRegister = (
  setErrorCode: React.Dispatch<React.SetStateAction<ErrorCode>>
): UseMutationResult<AxiosResponse<any, any>, AxiosError<unknown, any>, Credentials, unknown> => {
  const postRegister = (data: Credentials) => {
    return axios.post(`http://localhost/api/v1/user-management/users`, data);
  };
  const defineError = (code: GenericServerError, message: string): void => {
    if (code === GenericServerError.GENERIC_1) setErrorCode({ code, message });
    else if (code === GenericServerError.GENERIC_2) setErrorCode({ code, message });
    else if (code === GenericServerError.GENERIC_3) setErrorCode({ code, message });
  };
  const onError = ({ response }: AxiosError): void => {
    if (response != undefined) {
      const { data }: AxiosResponse = response;
      if (data) {
        defineError(data.code, data.message);
      }
    }
  };
  const onSuccess = ({ data }: AxiosResponse): void => {
    console.log(data);
  };
  return useMutation(postRegister, { onError, onSuccess });
};
