import MainHeader from "./MainHeader.jsx";
import Footer from "./Footer.jsx";
import { useState } from "react";
import { Outlet } from "react-router-dom";

function MainLayout() {

  return (
    <>
      <MainHeader />

      <Outlet />

      <Footer />
    </>
  );
}

export default MainLayout;
