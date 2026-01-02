import { Outlet } from 'react-router';
import './App.css';
import Navbar from './navbar/Navbar';
import api from './api/api';


function App() {
  return (
    <>
      <Navbar/>
      <Outlet/>
    </>

  );
}

export default App;
