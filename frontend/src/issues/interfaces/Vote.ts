import { VoteType } from '@issues/enums';

export interface Vote {
  memberId: string;
  type: VoteType;
}
