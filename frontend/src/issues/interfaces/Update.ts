import { UpdateType } from '@issues/enums';

export interface Update {
  type: UpdateType;
  previousValue: string;
  currentValue: string;
  updatedAt: string;
}
