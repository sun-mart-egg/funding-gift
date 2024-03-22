import React from "react";
import SearchBar from "../UI/SearchBar";
import { useNavigate } from "react-router-dom";

import CatIcon from "/imgs/cat.PNG";
import FishIcon from "/imgs/fish.PNG";

import ScrollToTop from "../UI/ScrollToTop";

function Home() {
  const navigate = useNavigate();

  return (
    <div className="main-layer font-cusFont2">
      <div>
        <SearchBar />
      </div>

      <div className="mt-[20px] h-[150px] w-[95%] rounded-md bg-gray-100 p-5">
        배너 들어갈 자리에요
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
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-gray-100 p-5">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-gray-100 p-5">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-gray-100 p-5">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="mt-[10px] h-[200px] w-full rounded-md bg-gray-100 p-5">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
      </div>
      <ScrollToTop />
    </div>
  );
}

export default Home;
