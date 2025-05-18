import React from "react";
import { Routes, Route } from "react-router-dom";
import Layout from '../src/components/Layout';
import  Home  from '../src/components/Home'
import SignUp from "./components/SignUp";
import Login from "./components/Login";
import Membership from "./components/Membership";

function App() {
  return (
    <Routes>
      <Route path="/signup" element={<SignUp />} />
      <Route path="/" element={<Layout />}>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/membership" element={<Membership/>} />
      </Route>
      <Route path="/login" element={<Login />} />
    </Routes>
  );
}

export default App;
