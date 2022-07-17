import { NotificationEventDto } from '@notifications/dtos/notification-event';

export interface OrganizationMemberInvitedEventDataDto {
  organizationId: string;
  memberId: string;
}
export interface OrganizationMemberInvitedEventDto
  extends NotificationEventDto<OrganizationMemberInvitedEventDataDto> {}
