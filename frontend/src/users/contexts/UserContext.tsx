import { AuthorizationConsts } from '@users/enums/authorization-consts';
import { createContext, useContext } from 'react';
import { useLocalStorage } from 'react-use';

interface ProviderProps {
  children: JSX.Element;
}
interface JwtContextProps {
  jwt: string | undefined;
  setJwt: React.Dispatch<string>;
  removeJwt: () => void;
}
const UserContext = createContext<JwtContextProps>({
  jwt: '',
  setJwt: null as any,
  removeJwt: null as any,
});

export const useJwt = () => {
  return useContext(UserContext);
};
export const UserProvider: React.FC<ProviderProps> = ({ children }) => {
  const [jwt, setJwt, removeJwt] = useLocalStorage<string>(AuthorizationConsts.JWT);
  return <UserContext.Provider value={{ jwt, setJwt, removeJwt }}>{children}</UserContext.Provider>;
};
