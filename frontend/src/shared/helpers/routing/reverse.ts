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
  if (isString(path)) return paths[path as Path];
  else {
    const pathWithParams = replace(
      replace(paths[path.path], ':userId', path.params.userId),
      ':activationToken',
      path.params.userId
    );
    return pathWithParams;
  }
};
