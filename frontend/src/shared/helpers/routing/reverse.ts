import { Path, paths } from '@shared/consts/routing';

export const reverse = (path: Path): string => {
  return paths[path];
};
