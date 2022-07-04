import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface OrganizationOwnerInvalidErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ORGANIZATION_OWNER_INVALID,
    HttpStatus.FORBIDDEN
  > {}
