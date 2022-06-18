import { AxiosError, AxiosResponse } from 'axios';
import { applicationErrorHandler } from '../../../shared/helpers/applicationErrorHandler';

export const onErrorRegister = ({ response }: AxiosError): void => {
  if (response != undefined) {
    const { data }: AxiosResponse = response;
    if (data) {
      console.log(applicationErrorHandler(data.code));
    }
  }
};
