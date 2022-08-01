import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface OrganizationAccessDeniedErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ORGANIZATION_ACCESS_DENIED,
    HttpStatus.FORBIDDEN
  > {}
