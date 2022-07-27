import { Status, Type, VoteType } from '@issues/enums/issues-list';

interface Vote {
  memberId: string;
  type: VoteType;
}

interface Creator {
  id: string;
  email: string;
}

interface Project {
  id: string;
}

interface Organization {
  id: string;
}

export interface IssuesListElement {
  id: string;
  name: string;
  status: Status;
  type: Type;
  votes: Vote[];
  creator: Creator;
  project: Project;
  organization: Organization;
}
export interface IssuesListDto extends Array<IssuesListElement> {}
