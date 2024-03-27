import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

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
  const location = useLocation();
  const currentPath =
    location.pathname !== "/" && location.pathname.endsWith("/")
      ? location.pathname.slice(0, -1)
      : location.pathname;
  let additionalLeft = 0;

  if (currentPath === ("/product" || "/product/")) {
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
        left: iconRect.left + iconRect.width / 2 - 20 + additionalLeft,
        bottom: 0,
        opacity: 1,
      });
    } else {
      setCatPawPosition({ ...catPawPosition, opacity: 0 }); // 경로가 일치하지 않으면 고양이 발 숨기기
    }
  };

  useEffect(() => {
    const path = currentPath === "/" ? "/" : currentPath.replace(/\/$/, "");

    updateCatPawPosition();
    window.addEventListener("resize", updateCatPawPosition);
    return () => {
      window.removeEventListener("resize", updateCatPawPosition);
    };
  }, [currentPath]); // currentPath 변경 시 업데이트

  const handleIconClick = (path) => {
    navigate(path);
  };

  return (
    <footer className="fixed bottom-0 flex w-full max-w-[500px] items-center justify-between bg-white px-10 pt-[10px] text-center font-cusFont2 shadow-top">
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
