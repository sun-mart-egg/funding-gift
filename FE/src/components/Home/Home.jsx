import React from "react";
import SearchBar from "../UI/SearchBar";
import { useNavigate } from "react-router-dom";

import CatIcon from "/imgs/cat_icon.png";
import FishIcon from "/icons/size-192.png";

function Home() {
  const navigate = useNavigate();

  return (
    <div className="relative flex min-h-screen flex-col items-center pt-4 font-cusFont2">
      <div className="w-full">
        <SearchBar />
      </div>

      <div className="mt-[20px] h-[150px] w-[95%] rounded-md bg-gray-100 p-5">
        배너 들어갈 자리에요
      </div>

      <div className="flex w-[95%]">
        <div
          onClick={() => navigate("/make-funding-main")}
          className="mr-[1.5%] mt-[15px] h-[100px] w-[50%] rounded-md bg-cusColor4 p-5 text-center"
        >
          <img src={CatIcon} alt="" className="mx-auto -mt-[15px] w-[38%]" />
          <p className="text-xs">펀딩 만들러 가기</p>
        </div>
        <div className="relative ml-[1.5%] mt-[15px] h-[100px] w-[50%] rounded-md bg-cusColor3 p-5 text-center">
          <img
            src={FishIcon}
            alt=""
            className="m-auto -mb-[20px] -mt-[25px] w-[55%]"
          />
          <p className="text-xs">펀딩 참여하러 가기</p>
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
    </div>
  );
}

export default Home;
