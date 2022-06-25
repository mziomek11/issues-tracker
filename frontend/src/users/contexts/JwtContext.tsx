import { createContext, useContext, useEffect } from 'react';
import { useLocalStorage } from 'react-use';
import { JWT } from '@users/consts/localstorage';
import { isTokenExpired } from '@users/helpers/jwt';

interface JwtState {
  jwt: string | undefined;
  setJwt: React.Dispatch<string>;
  removeJwt: () => void;
}
type JwtProviderProps = {
  children: React.ReactNode;
};

const JwtContext = createContext<JwtState>(null as any);

export const useJwt = (): JwtState => {
  return useContext(JwtContext);
};
export const JwtProvider: React.FC<JwtProviderProps> = ({ children }: JwtProviderProps) => {
  const [jwt, setJwt, removeJwt] = useLocalStorage<string>(JWT);
  useEffect(() => {
    if (!jwt) return;

    if (!isTokenExpired(jwt)) return removeJwt();
  }, []);
  return <JwtContext.Provider value={{ jwt, setJwt, removeJwt }}>{children}</JwtContext.Provider>;
};
