import { Status, IssueType } from '@issues/enums';
import { Creator, Organization, Project, Vote } from '@issues/interfaces';

export interface IssueListElementDto {
  id: string;
  name: string;
  status: Status;
  type: IssueType;
  votes: Vote[];
  creator: Creator;
  project: Project;
  organization: Organization;
}
