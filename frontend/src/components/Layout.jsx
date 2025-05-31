import React, { useEffect, useState, react } from 'react';

import { Outlet } from "react-router-dom";
import Header from "./Header"
import Footer from './Footer';

const Layout = () => {

  return (
    <main  className="App">
      <Header/>
        <div>  <Outlet /></div>
      <Footer/>
    </main>
  );
};



export default Layout;
