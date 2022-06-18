import react from '@vitejs/plugin-react';
import eslint from '@rollup/plugin-eslint';
import checker from 'vite-plugin-checker';
import tsconfigPaths from 'vite-tsconfig-paths';
import { defineConfig } from 'vite';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    tsconfigPaths(),
    checker({ typescript: true }),
    { ...eslint({ include: 'src/**/*.+(js|jsx|ts|tsx)' }), enforce: 'pre' },
    react(),
  ],
});
