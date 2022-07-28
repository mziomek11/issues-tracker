import { Type } from '@issues/enums/issues-list';

export interface CreateIssueDto {
  type: Type;
  name: string;
  content: string;
}
