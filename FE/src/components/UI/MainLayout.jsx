import MainHeader from "./MainHeader.jsx";
import Footer from "./Footer.jsx";
import { useState } from "react";
import { Outlet } from "react-router-dom";

function MainLayout() {

  return (
    <div className="flex min-h-screen w-full flex-col">
      <MainHeader />
      {/* Outlet 내용과 Footer가 겹치지 않도록 padding-bottom 추가 */}
      <div className=" w-full flex-grow pb-20">
        <Outlet />
      </div>
      {/* Footer는 여기에 그대로 유지 */}
      <Footer />
    </div>
  );
}

export default MainLayout;
