import { Path, paths } from './dtos/reverse-url.dto';

export const reverse = (path: Path): string => {
  return paths[path];
};
