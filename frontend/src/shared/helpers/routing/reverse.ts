import { isString, replace } from 'lodash';
import { Path, paths } from '@shared/consts/routing';
import { UserActivationParams } from '@users/types/activation';
import { OrganizationParams } from '@organizations/types';
import { IssueDetailsParams, IssuesListParams } from '@issues/types';

interface DetailedPath<TPath extends string, TParams extends Record<string, any>> {
  path: TPath;
  params: TParams;
}

type RegularPaths = Exclude<
  Path,
  | 'users.activation'
  | 'organizations.projects.create'
  | 'organizations.details'
  | 'organizations.member.invite'
  | 'issues.list'
  | 'issues.create'
  | 'issues.details'
>;

type ReversiblePath =
  | RegularPaths
  | DetailedPath<'users.activation', UserActivationParams>
  | DetailedPath<'organizations.projects.create', OrganizationParams>
  | DetailedPath<'organizations.details', OrganizationParams>
  | DetailedPath<'organizations.member.invite', OrganizationParams>
  | DetailedPath<'issues.list', IssuesListParams>
  | DetailedPath<'issues.create', IssuesListParams>
  | DetailedPath<'issues.details', IssueDetailsParams>;

export const reverse = (path: ReversiblePath): string => {
  if (isString(path)) return paths[path as Path];

  let pathWithParams = paths[path.path];
  Object.keys(path.params).forEach((param) => {
    pathWithParams = replace(
      pathWithParams,
      `:${String(param)}`,
      path.params[param as keyof ReversiblePath]
    );
  });

  return pathWithParams;
};
