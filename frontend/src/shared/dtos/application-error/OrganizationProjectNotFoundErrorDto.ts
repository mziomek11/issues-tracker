import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface OrganizationProjectNotFoundErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ORGANIZATION_PROJECT_NOT_FOUND,
    HttpStatus.NOT_FOUND
  > {}
