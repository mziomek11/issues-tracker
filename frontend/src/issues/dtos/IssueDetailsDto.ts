import { IssueComment, Update } from '@issues/interfaces';
import { IssueListElementDto } from './IssueListElementDto';

export interface IssueDetailsDto extends IssueListElementDto {
  updates: Update[];
  comments: IssueComment[];
}
