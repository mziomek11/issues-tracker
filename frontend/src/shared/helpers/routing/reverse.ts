import { Path, paths } from '@shared/consts/routing';
import { UserActivationParamsDto } from '@users/dtos';
import { isString, replace } from 'lodash';

interface DetailedPath<TPath extends string, TParams extends Record<string, any>> {
  path: TPath;
  params: TParams;
}

type RegularPaths = Exclude<Path, 'users.activation'>;

type ReversiblePath = RegularPaths | DetailedPath<'users.activation', UserActivationParamsDto>;

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
