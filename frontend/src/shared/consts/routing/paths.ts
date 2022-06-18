export type Path = 'users.register' | 'users.login';

export const paths: Record<Path, string> = {
  'users.login': '/login',
  'users.register': '/register',
};
