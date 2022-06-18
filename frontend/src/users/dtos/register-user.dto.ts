import { ApplicationErrorCode } from '../../shared/dtos/application-error.dto';

export interface RegisterFormFields {
  email: string;
  password: string;
  repeatPassword: string;
}

export interface RegisterUserDto {
  email: string;
  password: string;
}

export interface ResponseDataDto {
  code: ApplicationErrorCode;
  status: number;
  message: string;
  details?: {
    someField: string[][];
    anotherField: string[][];
  };
}
