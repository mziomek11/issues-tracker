export interface ListInvitationDto {
  member: string;
  organization: ListInvitationOrganizationDto;
}

export interface ListInvitationOrganizationDto {
  id: string;
  name: string;
}
