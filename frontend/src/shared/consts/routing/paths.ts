export type Path = 'users.register' | 'users.login' | 'users.activation';

export const paths: Record<Path, string> = {
  'users.login': '/login',
  'users.register': '/register',
  'users.activation': '/users/:userId/activation/:activationToken',
};
