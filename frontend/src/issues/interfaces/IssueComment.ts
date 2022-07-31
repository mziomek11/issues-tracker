import { CommentStatus } from '@issues/enums';
import { Creator, Update } from '@issues/interfaces';

export interface IssueComment {
  id: string;
  content: string;
  status: CommentStatus;
  creator: Creator;
  updates: Update[];
}
