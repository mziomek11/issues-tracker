import { AxiosResponse } from 'axios';

export const onSuccessRegister = ({ data }: AxiosResponse): void => {
  console.log(data);
};
