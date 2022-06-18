import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, UseMutationResult } from 'react-query';
import { RegisterUserDto, ResponseDataDto } from '../../dtos/register-user.dto';
import { register } from '../../api/register';

export const useRegister = (): UseMutationResult<
  AxiosResponse<string, RegisterUserDto>,
  AxiosError<ResponseDataDto, unknown>,
  RegisterUserDto,
  unknown
> => {
  return useMutation(register);
};
