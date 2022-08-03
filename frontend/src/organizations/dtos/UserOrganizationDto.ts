interface Member {
  id: string;
  email: string;
}
export interface ProjectDto {
  id: string;
  name: string;
}
export interface UserOrganizationDto {
  id: string;
  name: string;
  ownerId: string;
  members: Member[];
  projects: ProjectDto[];
}
