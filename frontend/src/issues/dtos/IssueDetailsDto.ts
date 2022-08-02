import {
  CommentStatus,
  CommentUpdateType,
  IssueStatus,
  IssueType,
  IssueUpdateType,
  VoteType,
} from '@issues/enums';

export interface IssueDetailsDto {
  id: string;
  name: string;
  content: string;
  status: IssueStatus;
  type: IssueType;
  votes: IssueDetailsVoteDto[];
  updates: Array<
    | IssueDetailsUpdateContentChangedDto
    | IssueDetailsUpdateRenamedDto
    | IssueDetailsUpdateTypeChangeDto
  >;
  comments: IssueDetailsCommentDto[];
  creator: IssueDetailsCreatorDto;
  project: IssueDetailsProjectDto;
  organization: IssueDetailsOrganizationDto;
}

export interface IssueDetailsVoteDto {
  memberId: string;
  type: VoteType;
}

export interface IssueDetailsUpdateDto<T, U> {
  type: T;
  updatedAt: string;
  previousValue: U;
  currentValue: U;
}

export type IssueDetailsUpdateContentChangedDto = IssueDetailsUpdateDto<
  IssueUpdateType.CONTENT_CHANGED,
  string
>;

export type IssueDetailsUpdateRenamedDto = IssueDetailsUpdateDto<IssueUpdateType.RENAMED, string>;

export type IssueDetailsUpdateTypeChangeDto = IssueDetailsUpdateDto<
  IssueUpdateType.TYPE_CHANGED,
  IssueType
>;

export interface IssueDetailsCommentDto {
  id: string;
  content: string;
  status: CommentStatus;
  creator: IssueDetailsCreatorDto;
  updates: Array<IssueDetailsCommentUpdateContentChangedDto>;
  votes: IssueDetailsVoteDto[];
}

export type IssueDetailsCommentUpdateContentChangedDto = IssueDetailsUpdateDto<
  CommentUpdateType.CONTENT_CHANGED,
  string
>;

export interface IssueDetailsCreatorDto {
  id: string;
  email: string;
}

export interface IssueDetailsProjectDto {
  id: string;
}

export interface IssueDetailsOrganizationDto {
  id: string;
}
