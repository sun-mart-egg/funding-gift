import React from "react";

import Footer from "../UI/Footer";

import SearchBar from "../UI/SearchBar";
import ScrollToTopButton from "../UI/ScrollToTop2";


function BrandStore() {
  return (
    <div className="sub-layer mt-[80px] justify-start min-h-screen overflow-hidden font-cusFont2 bg-white">

      <div className="ml-[13%] flex text-left w-full">
        <p className="text-4xl font-cusFont5 mb-[30px]">브랜드 LOGO</p>
        <p className="ml-[30px]">브랜드 설명</p>
      </div>

      <div className="h-[10%] w-[90.5%]">
        <SearchBar />
      </div>

      <div className="my-[20px] flex w-[95.5%] flex-grow flex-wrap justify-center bg-white">
        {/* 상품 박스들 */}
        {[1, 2, 3, 4].map((id) => (
          <div key={id} className="m-2 h-[58.5%] w-[45%] flex-col rounded-md border border-gray-300 text-[12px]">
            <div className="image-area h-[70%] w-[100%] bg-gray-200 rounded-md">
              {/* 빈 이미지 대신 사용될 배경 */}
            </div>
            <div className="info-area m-[1px] flex h-[30%] w-[100%] flex-col justify-center p-[10px] pl-2">
              <p className="name-placeholder bg-gray-200 rounded">상품 이름</p>
              <p className="price-placeholder bg-gray-200 rounded">가격 정보</p>
              <p className="review-placeholder flex items-center">
                <span className="bg-gray-200 rounded w-[12px] h-[12px]"></span>
                <span className="ml-1 bg-gray-200 rounded">리뷰 정보</span>
              </p>
            </div>
          </div>
        ))}
      </div>
      <ScrollToTopButton />
    </div>
  )
        };


  export default BrandStore;
