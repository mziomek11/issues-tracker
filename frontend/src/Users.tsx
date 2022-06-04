/* eslint-disable */

import { FormEvent, useState } from 'react';
import { post, subscribe } from './http';

export const Users = () => {
  const [registerForm, setRegisterForm] = useState({ email: '', password: '' });

  const register = (e: FormEvent) => {
    e.preventDefault();
    post('/api/v1/user-management/users', registerForm);
  };

  const login1 = async (e: FormEvent) => {
    e.preventDefault();
    (window as any).jwt = await post('/api/v1/user-management/users/authentication', {
      email: 'nowtyser123@test.bla',
      password: 'test12123123',
    });
  };

  const login2 = async (e: FormEvent) => {
    e.preventDefault();
    (window as any).jwt = await post('/api/v1/user-management/users/authentication', {
      email: 'admin17@admin.com',
      password: 'password',
    });
  };

  return (
    <div>
      <h1>Users</h1>
      <button onClick={login1}>Login1</button>
      <button onClick={login2}>Login2</button>
      <form onSubmit={subscribe}>
        <button>Subscribe</button>
      </form>

      <form onSubmit={register}>
        <h2>Register form</h2>
        <label>Email</label>
        <input
          value={registerForm.email}
          onChange={(e) => setRegisterForm({ ...registerForm, email: e.target.value })}
        />

        <label>Password</label>
        <input
          value={registerForm.password}
          onChange={(e) => setRegisterForm({ ...registerForm, password: e.target.value })}
        />
        <button>Register</button>
      </form>
    </div>
  );
};
