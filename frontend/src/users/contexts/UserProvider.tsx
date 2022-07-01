import React, { createContext, PropsWithChildren, useContext, useEffect } from 'react';
import { useLocalStorage } from 'react-use';
import { JWT } from '@users/consts/localstorage';
import { isTokenExpired } from '@users/helpers/jwt';

interface UserState {
  loginUser: (jwt: string) => void;
  logoutUser: () => void;
  isLoggedIn: boolean;
  jwt: string | undefined;
}
interface UserProviderProps {}

const UserContext = createContext<UserState>(null as any);

export const useUser = (): UserState => useContext(UserContext);

export const UserProvider: React.FC<PropsWithChildren<UserProviderProps>> = ({ children }) => {
  const [jwt, setJwt, removeJwt] = useLocalStorage<string>(JWT);

  const isLoggedIn = !!jwt;
  const loginUser = (jwt: string): void => {
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
