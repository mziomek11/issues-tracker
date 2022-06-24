import { createContext, useContext } from 'react';
import { useLocalStorage } from 'react-use';
import { AuthorizationConsts } from '@users/enums/authorization-consts';

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
  setJwt: () => {},
  removeJwt: () => {},
});

export const useJwt = () => {
  return useContext(UserContext);
};
export const UserProvider: React.FC<ProviderProps> = ({ children }) => {
  const [jwt, setJwt, removeJwt] = useLocalStorage<string>(AuthorizationConsts.JWT);
  return <UserContext.Provider value={{ jwt, setJwt, removeJwt }}>{children}</UserContext.Provider>;
};
