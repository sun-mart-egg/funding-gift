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
    <footer className="font-cusFont2 text-center fixed bottom-0 pt-[10px] shadow-top flex w-full max-w-[500px] items-center justify-between bg-white px-10">
      <div>
        <img
          src={getIconStatus("/") ? HomeActivated : Home}
          alt="Home"
          className={` ${getIconStatus("/") ? "h-[65px] w-[45px]" : "h-[25px] w-[25px]"}`}
          onClick={() => handleIconClick("/")}
        />
        {!getIconStatus("/") && <span className="text-[10px]">홈</span>}
      </div>
      <div>
        <img
          src={getIconStatus("/product") ? ShopActivated : Shop}
          alt="Shop"
          className={`${getIconStatus("/product") ? "h-[65px] w-[45px]" : "h-[25px] w-[25px]"}`}
          onClick={() => handleIconClick("/product")}
        />
        {!getIconStatus("/product") && <span className="text-[10px]">쇼핑</span>}
      </div>
      <div>
        <img
          src={getIconStatus("/fund") ? FundActivated : Fund}
          alt="Fund"
          className={`${getIconStatus("/fund") ? "h-[65px] w-[45px]" : "h-[25px] w-[25px]"}`}
          onClick={() => handleIconClick("/fund")}
        />
        {!getIconStatus("/fund") && <span className="text-[10px]">펀딩</span>}
      </div>
      <div>
        <img
          src={getIconStatus("/my-funding") ? ProfileActivated : Profile}
          alt="Profile"
          className={`${getIconStatus("/my-funding") ? "h-[65px] w-[45px]" : "h-[25px] w-[25px]"}`}
          onClick={() => handleIconClick("/my-funding")}
        />
        {!getIconStatus("/my-funding") && <span className="text-[10px]">프로필</span>}
      </div>
    </footer>
  );
}

export default Footer;
