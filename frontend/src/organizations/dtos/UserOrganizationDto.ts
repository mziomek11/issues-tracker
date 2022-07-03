interface Member {
  id: string;
}
interface Project {
  id: string;
  name: string;
}
export interface UserOrganizationDto {
  id: string;
  name: string;
  ownerId: string;
  members: Member[];
  projects: Project[];
}
