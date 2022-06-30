export type Path =
  | 'shared.home'
  | 'users.register'
  | 'users.login'
  | 'users.activation'
  | 'organizations.create';

export const paths: Record<Path, string> = {
  'shared.home': '/',
  'users.login': '/login',
  'users.register': '/register',
  'users.activation': '/users/:userId/activation/:activationToken',
  'organizations.create': '/organizations/create',
};
