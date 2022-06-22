import { isString, replace } from 'lodash';
import { Path, paths } from '@shared/consts/routing';
import { UserActivationParams } from '@users/types/activation';

interface DetailedPath<TPath extends string, TParams extends Record<string, any>> {
  path: TPath;
  params: TParams;
}

type RegularPaths = Exclude<Path, 'users.activation'>;

type ReversiblePath = RegularPaths | DetailedPath<'users.activation', UserActivationParams>;

export const reverse = (path: ReversiblePath): string => {
  let pathWithParams: string;
  if (isString(path)) {
    pathWithParams = paths[path as Path];
  } else {
    pathWithParams = paths[path.path];
    Object.keys(path.params).forEach((param) => {
      pathWithParams = replace(
        pathWithParams,
        `:${String(param)}`,
        path.params[param as keyof ReversiblePath]
      );
    });
  }
  return pathWithParams;
};
