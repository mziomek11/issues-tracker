import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface OrganizationInvitationNotFoundErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ORGANIZATION_INVITATION_NOT_FOUND,
    HttpStatus.NOT_FOUND
  > {}
