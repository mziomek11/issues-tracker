import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { ApplicationErrorDto } from './ApplicationErrorDto';

export interface OrganizationInvitationAlreadyPresentErrorDto
  extends ApplicationErrorDto<
    ApplicationErrorCode.ORGANIZATION_INVITATION_ALREADY_PRESENT,
    HttpStatus.CONFLICT
  > {}
