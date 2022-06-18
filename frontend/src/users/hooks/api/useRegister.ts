import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { RegisterUserDto } from '../../dtos/register-user.dto';
import { register } from '../../api/register';

export const useRegister = (
  onError: ({ response }: AxiosError) => void,
  onSuccess: ({ data }: AxiosResponse) => void
): UseMutationResult<
  AxiosResponse<string, RegisterUserDto>,
  AxiosError<unknown, any>,
  RegisterUserDto,
  unknown
> => {
  return useMutation(register, { onError, onSuccess });
};
