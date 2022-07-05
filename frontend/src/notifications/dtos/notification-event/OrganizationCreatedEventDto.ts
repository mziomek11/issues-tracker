import { NotificationEventDto } from './NotificationEventDto';

export interface OrganizationCreatedEventDataDto {
  organizationId: string;
}

export interface OrganizationCreatedEventDto
  extends NotificationEventDto<OrganizationCreatedEventDataDto> {}
