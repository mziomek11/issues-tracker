import { createContext, useContext, useEffect } from 'react';
import { useLocalStorage } from 'react-use';
import { JWT } from '@users/consts/localstorage';
import { isTokenExpired } from '@users/helpers/jwt';

interface UserState {
  loginUser: (jwt: string | undefined) => void;
  logoutUser: () => void;
  isLoggedIn: boolean;
  jwt: string | undefined;
}
type UserProviderProps = {
  children: React.ReactNode;
};

const UserContext = createContext<UserState>(null as any);

export const useUser = (): UserState => useContext(UserContext);

export const UserProvider: React.FC<UserProviderProps> = ({ children }: UserProviderProps) => {
  const [jwt, setJwt, removeJwt] = useLocalStorage<string>(JWT);

  const isLoggedIn = !!jwt;
  const loginUser = (jwt: string | undefined): void => {
    setJwt(jwt);
  };
  const logoutUser = (): void => {
    removeJwt();
  };

  useEffect(() => {
    if (!jwt) return;

    if (!isTokenExpired(jwt)) return removeJwt();
  }, []);

  return (
    <UserContext.Provider value={{ loginUser, logoutUser, isLoggedIn, jwt }}>
      {children}
    </UserContext.Provider>
  );
};
