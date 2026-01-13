import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { RouterProvider } from 'react-router';
import { router } from './Routes';
import { AuthProvider } from './providers/AuthProvider';
import { ShoppingCartProvider } from './components/context/ShoppingCartContext';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
    <AuthProvider>
      <ShoppingCartProvider>
        <RouterProvider router={router}/>
      </ShoppingCartProvider>
    </AuthProvider>
  </React.StrictMode>
);

reportWebVitals();