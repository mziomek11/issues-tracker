import { Users } from './Users';
import { Organizations } from './Organizations';
import { Issues } from './Issues';
import './style.css';

export const App: React.FC = () => {
  return (
    <div>
      <div>
        <p>First Org: df763815-d06d-46ab-a0a5-0ad35dc64e45</p>
        <p>First Proj: c9c6c3a4-1d09-4269-a9ef-549f57d73453</p>
      </div>
      <div style={{ display: 'flex' }}>
        <Users />
        <Organizations />
        <Issues />
      </div>
    </div>
  );
};
