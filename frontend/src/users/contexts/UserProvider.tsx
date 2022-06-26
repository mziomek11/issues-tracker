import { createContext, useContext, useEffect } from 'react';
import { useLocalStorage } from 'react-use';
import { JWT } from '@users/consts/localstorage';
import { isTokenExpired } from '@users/helpers/jwt';
import { subscribe, userAbortController } from '@users/api';

interface UserState {
  loginUser: (jwt: string | undefined) => void;
  logoutUser: () => void;
  isLoggedIn: boolean;
}
type UserProviderProps = {
  children: React.ReactNode;
};

const UserContext = createContext<UserState>(null as any);

export const useUser = (): UserState => {
  return useContext(UserContext);
};

export const UserProvider: React.FC<UserProviderProps> = ({ children }: UserProviderProps) => {
  const [jwt, setJwt, removeJwt] = useLocalStorage<string>(JWT);
  subscribe(jwt);

  const isLoggedIn = !!jwt;
  const loginUser = (jwt: string | undefined) => {
    setJwt(jwt);
  };
  const logoutUser = () => {
    removeJwt();
    userAbortController.abort();
  };

  useEffect(() => {
    if (!jwt) return;

    if (!isTokenExpired(jwt)) return removeJwt();
  }, []);
  return (
    <UserContext.Provider value={{ loginUser, logoutUser, isLoggedIn }}>
      {children}
    </UserContext.Provider>
  );
};
