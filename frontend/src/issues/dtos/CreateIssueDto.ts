import { IssueType } from '@issues/enums';

export interface CreateIssueDto {
  type: IssueType;
  name: string;
  content: string;
}
