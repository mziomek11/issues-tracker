import { NotificationEventDto } from '@notifications/dtos/notification-event';

export interface OrganizationMemberJoinedEventDataDto {
  organizationId: string;
  memberId: string;
}
export interface OrganizationMemberJoinedEventDto
  extends NotificationEventDto<OrganizationMemberJoinedEventDataDto> {}
