import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface OrganizationNotFoundErrorDto
  extends ApplicationErrorDto<ApplicationErrorCode.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND> {}
