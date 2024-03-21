import React, { useState } from "react";
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
  const [activeIcon, setActiveIcon] = useState("home");

  const navigate = useNavigate();

  const handleIconClick = (icon, path) => {
    setActiveIcon(icon); // 아이콘 상태 업데이트
    navigate(path); // 페이지 이동
  };

  return (
    <footer className="fixed bottom-0 left-0  flex w-full max-w-[500px] items-center justify-between border-t border-gray-400 bg-white px-10">
      <img
        src={activeIcon === "home" ? HomeActivated : Home}
        alt="Home"
        className={` ${activeIcon === "home" ? "h-[65px] w-[45px]" : "h-[45px] w-[35px]"}`}
        onClick={() => handleIconClick("home", "/")}
      />
      <img
        src={activeIcon === "shop" ? ShopActivated : Shop}
        alt="Shop"
        className={`${activeIcon === "shop" ? "h-[65px] w-[45px]" : "h-[45px] w-[35px]"}`}
        onClick={() => handleIconClick("shop", "/product")}
      />
      <img
        src={activeIcon === "fund" ? FundActivated : Fund}
        alt="Fund"
        className={`w-[40px] ${activeIcon === "fund" ? "h-[65px] w-[45px]" : "h-[45px] w-[35px]"}`}
        // 펀딩 메인 페이지는 수정해주세요
        onClick={() => handleIconClick("fund", "/")}
      />
      <img
        src={activeIcon === "profile" ? ProfileActivated : Profile}
        alt="Profile"
        className={`w-[40px] ${activeIcon === "profile" ? "h-[65px] w-[45px]" : "h-[45px] w-[35px]"}`}
        // 프로필 페이지 맞는지 확인해주세요
        onClick={() => handleIconClick("profile", "/profile")}
      />
    </footer>
  );
}

export default Footer;
