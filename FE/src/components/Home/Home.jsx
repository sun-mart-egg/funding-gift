import React from "react";
import SearchBar from "../UI/SearchBar";

import CatIcon from "/imgs/cat_icon.png"
import FishIcon from "/icons/size-192.png"

function Home() {
  return (
    <div className="main-layer item font-cusFont2">

      <div className="w-full">
        <SearchBar />
      </div>

      <div className="w-[95%] mt-[20px] p-5 h-[150px] bg-gray-100 rounded-md">
        배너 들어갈 자리에요
      </div>

      <div className="flex w-[95%]">
        <div className="w-[50%] mt-[15px] mr-[1.5%] p-5 h-[100px] rounded-md bg-cusColor4 text-center">
          <img src={CatIcon} alt="" className="-mt-[15px] mx-auto w-[38%]" />
          <p className="text-xs">펀딩 만들러 가기</p>
        </div>
        <div className="relative w-[50%] mt-[15px] ml-[1.5%] p-5 h-[100px] rounded-md bg-cusColor3 text-center">
          <img src={FishIcon} alt="" className="-mt-[25px] -mb-[20px] m-auto w-[55%]"/>
          <p className="text-xs">펀딩 참여하러 가기</p>
        </div>
      </div>

      <div className="w-[95%] mt-[20px] text-left">
        
        <p className="font-cusFont5 text-2xl">추천 상품</p>
        <div className="w-full mt-[10px] p-5 rounded-md h-[200px] bg-gray-100">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="w-full mt-[10px] p-5 rounded-md h-[200px] bg-gray-100">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="w-full mt-[10px] p-5 rounded-md h-[200px] bg-gray-100">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
        <div className="w-full mt-[10px] p-5 rounded-md h-[200px] bg-gray-100">
          <p className="">추천 상품이 들어갈 예정입니다!</p>
        </div>
      </div>
    </div>
  );
}

export default Home;
