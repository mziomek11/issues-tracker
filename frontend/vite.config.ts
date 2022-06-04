import react from '@vitejs/plugin-react';
import eslint from '@rollup/plugin-eslint';
import checker from 'vite-plugin-checker';
import { defineConfig } from 'vite';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    checker({ typescript: true }),
    { ...eslint({ include: 'src/**/*.+(js|jsx|ts|tsx)' }), enforce: 'pre' },
    react(),
  ],
});
