const MILISECONDS = 1000;
const MINUTE = 60 * MILISECONDS;
export const tokenExpirationCheck = (token: string): boolean => {
  const base64Url: string = token.split('.')[1];
  const mappedString = atob(base64Url)
    .split('')
    .map((c) => {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    })
    .join('');
  const base64 = JSON.parse(decodeURIComponent(mappedString));
  return base64.exp * MILISECONDS - MINUTE > Date.now();
};
