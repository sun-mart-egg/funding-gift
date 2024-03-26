import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Home from "/imgs/footer_home.png";
import HomeActivated from "/imgs/footer_home_activated.png";
import Shop from "/imgs/footer_shop.png";
import ShopActivated from "/imgs/footer_shop_activated.png";
import Fund from "/imgs/footer_fund.png";
import FundActivated from "/imgs/footer_fund_activated.png";
import Profile from "/imgs/footer_profile.png";
import ProfileActivated from "/imgs/footer_profile_activated.png";
import CatPaw from "/imgs/cat_paw.png";

function Footer() {
  const navigate = useNavigate();
  const currentPath = window.location.pathname;
  let additionalLeft = 0;

  if (currentPath === "/product") {
    additionalLeft = 1.5;
  }

  const [catPawPosition, setCatPawPosition] = useState({
    left: 0,
    bottom: 0,
    opacity: 0,
  });

  const getIconStatus = (iconName) => {
    return currentPath === iconName;
  };

  const updateCatPawPosition = () => {
    const paths = ["/", "/product", "/funding", "/my-funding"];
    const currentIconIndex = paths.indexOf(currentPath);
    if (currentIconIndex !== -1) {
      const currentIcon =
        document.querySelectorAll(".footer-icon")[currentIconIndex];
      const iconRect = currentIcon.getBoundingClientRect();
      setCatPawPosition({
        left: iconRect.left + iconRect.width / 2 - 20 + additionalLeft, // Adjust based on cat paw icon size
        bottom: 0, // Adjust based on footer height
        opacity: 1,
      });
    }
  };

  useEffect(() => {
    updateCatPawPosition();
    window.addEventListener("resize", updateCatPawPosition);
    return () => {
      window.removeEventListener("resize", updateCatPawPosition);
    };
  }, [currentPath]);

  const handleIconClick = (path) => {
    navigate(path);
  };

  return (
    <footer className="shadow-top fixed bottom-0 flex w-full max-w-[500px] items-center justify-between bg-white px-10 pt-[10px] text-center font-cusFont2">
      {["/", "/product", "/funding", "/my-funding"].map((path, index) => (
        <div
          key={index}
          className="footer-icon"
          onClick={() => handleIconClick(path)}
        >
          <img
            src={
              getIconStatus(path)
                ? [
                    HomeActivated,
                    ShopActivated,
                    FundActivated,
                    ProfileActivated,
                  ][index]
                : [Home, Shop, Fund, Profile][index]
            }
            alt={["Home", "Shop", "Fund", "Profile"][index]}
            className={
              getIconStatus(path)
                ? "mb-[25px] h-[40px] w-[40px]"
                : "h-[25px] w-[25px]"
            }
          />
          {!getIconStatus(path) && (
            <span className="text-[10px]">
              {["홈", "쇼핑", "펀딩", "프로필"][index]}
            </span>
          )}
        </div>
      ))}
      <img
        src={CatPaw}
        className="h-[25px] w-[40px]"
        style={{
          position: "absolute",
          ...catPawPosition,
          transition: "left 0.5s ease, bottom 0.5s ease, opacity 0.5s ease",
        }}
      />
    </footer>
  );
}

export default Footer;
