export type Path =
  | 'shared.home'
  | 'users.register'
  | 'users.login'
  | 'users.activation'
  | 'organizations.list'
  | 'organizations.create';

export const paths: Record<Path, string> = {
  'shared.home': '/',
  'users.login': '/login',
  'users.register': '/register',
  'users.activation': '/users/:userId/activation/:activationToken',
  'organizations.list': '/organizations',
  'organizations.create': '/organizations/create',
};
