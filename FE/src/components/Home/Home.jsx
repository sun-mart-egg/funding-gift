import React, { useState, useEffect } from "react";
import SearchBar from "../UI/SearchBar";
import { useNavigate } from "react-router-dom";

import CatIcon from "/imgs/cat.PNG";
import FishIcon from "/imgs/fish.PNG";

import BannerImage1 from "/imgs/banner_image1.png"
import BannerImage2 from "/imgs/banner_image2.png"
import BannerImage3 from "/imgs/banner_image3.png"


import ScrollToTop from "../UI/ScrollToTop";

function Home() {
  const navigate = useNavigate();

  const [currentBanner, setCurrentBanner] = useState(0);
  const bannerImages = [BannerImage1, BannerImage2, BannerImage3];

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentBanner((prevIndex) => (prevIndex + 1) % bannerImages.length);
    }, 5000); // Change image every 5000 milliseconds

    return () => clearInterval(interval); // Clean up interval on component unmount
  }, []); // Empty dependency array means this runs once on mount

  return (
    <div className="main-layer font-cusFont2">
      <div className="w-full">
        <SearchBar />
      </div>

      <div className="mt-5 h-[150px] w-[95%] rounded-md relative overflow-hidden">
        {bannerImages.map((image, index) => (
          <img
            key={index}
            src={image}
            alt={`Banner ${index + 1}`}
            className={`absolute top-0 left-0 h-full w-full rounded-lg object-cover transition-opacity duration-1000 ${index === currentBanner ? 'opacity-100' : 'opacity-0'}`}
          />
        ))}
      </div>

      <div className="flex w-[95%]">
        <div
          onClick={() => navigate("/make-funding-main")}
          className="relative mr-[1.5%] mt-[15px] h-[100px] w-[50%] rounded-md bg-cusColor4 p-5 text-center"
        >
          <img src={CatIcon} alt="" className="absolute top-[5%] left-1/2 transform -translate-x-1/2 w-[68px] h-[70.5px]" />
          <p className="text-xs absolute left-0 right-0 bottom-2 w-full">펀딩 만들러 가기</p>
        </div>
        <div
          className="relative ml-[1.5%] mt-[15px] h-[100px] w-[50%] rounded-md bg-cusColor3 p-5 text-center"
        >
          <img src={FishIcon} alt="" className="absolute top-[5%] left-1/2 transform -translate-x-1/2 w-[92px] h-[71px]" />
          <p className="text-xs absolute left-0 right-0 bottom-2 w-full">펀딩 참여하러 가기</p>
        </div>
      </div>

      <div className="mt-[20px] w-[95%] text-left">
        <p className="font-cusFont5 text-2xl">추천 상품</p>
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-red-50 p-5">
          <p className="font-cusFont4">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-red-50 p-5">
          <p className="font-cusFont4">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-red-50 p-5">
          <p className="font-cusFont4">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-red-50 p-5">
          <p className="font-cusFont4">추천 상품이 들어갈 예정입니다!</p>
        </div>
      </div>

      <ScrollToTop className="bottom-[25px]" />
    </div>
  );
}

export default Home;
