import React from "react";
import { useNavigate } from "react-router-dom";

import Home from "/imgs/footer_home.png";
import HomeActivated from "/imgs/footer_home_activated.png";
import Shop from "/imgs/footer_shop.png";
import ShopActivated from "/imgs/footer_shop_activated.png";
import Fund from "/imgs/footer_fund.png";
import FundActivated from "/imgs/footer_fund_activated.png";
import Profile from "/imgs/footer_profile.png";
import ProfileActivated from "/imgs/footer_profile_activated.png";

function Footer() {
  const navigate = useNavigate();
  const currentPath = window.location.pathname; // 현재 경로 가져오기

  const getIconStatus = (iconName) => {
    return currentPath === iconName ? true : false;
  };

  const handleIconClick = (path) => {
    navigate(path); // 페이지 이동
  };

  return (
    <footer className="fixed bottom-0  flex w-full max-w-[500px] items-center justify-between border-t border-gray-400 bg-white px-10">
      <img
        src={getIconStatus("/") ? HomeActivated : Home}
        alt="Home"
        className={` ${getIconStatus("/") ? "h-[65px] w-[45px]" : "h-[40px] w-[30px]"}`}
        onClick={() => handleIconClick("/")}
      />
      <img
        src={getIconStatus("/product") ? ShopActivated : Shop}
        alt="Shop"
        className={`${getIconStatus("/product") ? "h-[65px] w-[45px]" : "h-[35px] w-[30px]"}`}
        onClick={() => handleIconClick("/product")}
      />
      <img
        src={getIconStatus("/fund") ? FundActivated : Fund}
        alt="Fund"
        className={`${getIconStatus("/fund") ? "h-[65px] w-[45px]" : "h-[35px] w-[27px]"}`}
        onClick={() => handleIconClick("/fund")}
      />
      <img
        src={getIconStatus("/my-funding") ? ProfileActivated : Profile}
        alt="Profile"
        className={`${getIconStatus("/my-funding") ? "h-[65px] w-[45px]" : "h-[35px] w-[27px]"}`}
        onClick={() => handleIconClick("/my-funding")}
      />
    </footer>
  );
}

export default Footer;
