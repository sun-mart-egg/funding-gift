import React, { useState } from "react";
import { useLocation } from "react-router-dom"; // useLocation을 import합니다.
import egg from "/imgs/egg3.jpg";

function Paypage() {
  const location = useLocation(); // 현재 location 객체를 가져옵니다.
  const { amount } = location.state; // location.state에서 amount 값을 추출합니다.
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);

  // 결제 수단을 선택하는 함수입니다.
  const selectPaymentMethod = (method) => {
    setSelectedPaymentMethod(method);
  };

  return (
    <div className="sub-layer flex justify-around pt-20 font-cusFont3">
      <div className="flex  w-[80%] flex-col justify-start">
        <p className=" pb-2 font-cusFont2 text-[20px]">펀딩 정보</p>
        <div
          id="fundingInfo"
          className="flex h-[150px] items-center justify-center rounded-md border border-gray-400 pt-1"
        >
          <div id="left" className="w-[40%]">
            <img src={egg} alt="" />
          </div>
          <div
            id="right"
            className="flex flex-col items-start justify-start p-2  text-xs"
          >
            <p>펀딩명 : MUSIC IS MY LIFE</p>
            <p>상품명 : 에어팟 맥스</p>
            <p>수령자 : 신시은</p>
          </div>
        </div>
      </div>
      <div className="w-[80%]">
        <p className=" pb-2 pt-4 font-cusFont2 text-[20px]">결제 수단</p>
        <div className="flex justify-between">
          <button
            className={`h-[50px] w-[30%]  border-2  bg-white ${selectedPaymentMethod === "카드결제" ? "rounded-md border-4 border-cusColor3" : "border-cusColor1"}`}
            onClick={() => selectPaymentMethod("카드결제")}
          >
            카드결제
          </button>
          <button
            className={`h-[50px] w-[30%] bg-green-500 ${selectedPaymentMethod === "네이버페이" ? "rounded-md border-4 border-cusColor3" : ""}`}
            onClick={() => selectPaymentMethod("네이버페이")}
          >
            네이버페이
          </button>
          <button
            className={`h-[50px] w-[30%] bg-yellow-400 ${selectedPaymentMethod === "카카오페이" ? "rounded-md border-4 border-cusColor3" : ""}`}
            onClick={() => selectPaymentMethod("카카오페이")}
          >
            카카오페이
          </button>
        </div>
      </div>

      <div className="w-[80%] pb-10">
        <p className="pt-4 font-cusFont2 text-[20px]">총 결제 예정 금액</p>
        <p className="font-cusFont2 text-[35px] text-cusColor3">
          {Number(amount).toLocaleString()} 원
        </p>
      </div>
      <button className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white">
        결제하기
      </button>

      {/* {selectedPaymentMethod && (
        <p>선택된 결제 수단: {selectedPaymentMethod}</p>
      )} */}
    </div>
  );
}

export default Paypage;
