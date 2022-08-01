import { IssueStatus, IssueType, VoteType } from '@issues/enums';

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
  status: IssueStatus;
  type: IssueType;
  votes: Vote[];
  creator: Creator;
  project: Project;
  organization: Organization;
}
export interface IssuesListDto extends Array<IssuesListElement> {}
