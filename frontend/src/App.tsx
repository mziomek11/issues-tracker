// import { Users } from './Users';
// import { Organizations } from './Organizations';
// import { Issues } from './Issues';
import { Login } from './users/pages/Login';
import './style.css';
import { ChakraProvider } from '@chakra-ui/react';
import { Route, Routes } from 'react-router-dom';
import { Register } from './users/pages/Register';
import { QueryClient, QueryClientProvider } from 'react-query';
const queryClient = new QueryClient()
export const App: React.FC = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <ChakraProvider>
        <Routes>
          <Route path='/login' element={ <Login />} />
          <Route path='/register' element={ <Register />} />
        </Routes>
        {/* <div>
          <p>First Org: df763815-d06d-46ab-a0a5-0ad35dc64e45</p>
          <p>First Proj: c9c6c3a4-1d09-4269-a9ef-549f57d73453</p>
        </div>
        <div style={{ display: 'flex' }}>
          <Users />
          <Organizations />
          <Issues />
        </div> */}
      </ChakraProvider>
    </QueryClientProvider>
  );
};
