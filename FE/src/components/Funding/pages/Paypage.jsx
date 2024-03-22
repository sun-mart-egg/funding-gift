import React, { useState } from "react";
import { useLocation } from "react-router-dom"; // useLocation을 import합니다.

function Paypage() {
  const location = useLocation(); // 현재 location 객체를 가져옵니다.
  const { amount } = location.state; // location.state에서 amount 값을 추출합니다.
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);

  // 결제 수단을 선택하는 함수입니다.
  const selectPaymentMethod = (method) => {
    setSelectedPaymentMethod(method);
  };

  return (
    <div className="main-layer">
      <p>펀딩 정보</p>
      <div id="fundingInfo" className="border border-black">
        <div id="left">
          <img src="" alt="" />
        </div>
        <div id="right">
          <p>펀딩명 : MUSIC IS MY LIFE</p>
          <p>상품명 : 에어팟 맥스</p>
          <p>수령자 : 신시은</p>
        </div>
      </div>
      <p>결제 수단</p>
      <div className="flex">
        <button
          className={`bg-gray-400 ${selectedPaymentMethod === "카드결제" ? "border-4 border-cusColor3" : ""}`}
          onClick={() => selectPaymentMethod("카드결제")}
        >
          카드결제
        </button>
        <button
          className={`bg-green-500 ${selectedPaymentMethod === "네이버페이" ? "border-4 border-cusColor3" : ""}`}
          onClick={() => selectPaymentMethod("네이버페이")}
        >
          네이버페이
        </button>
        <button
          className={`bg-yellow-400 ${selectedPaymentMethod === "카카오페이" ? "border-4 border-cusColor3" : ""}`}
          onClick={() => selectPaymentMethod("카카오페이")}
        >
          카카오페이
        </button>
      </div>

      <p>총 결제 예정 금액</p>
      <p className="text-cusColor3"> {amount} 원</p>
      <button className="fixed bottom-0 left-28 bg-cusColor3 py-3 text-white">
        결제하기
      </button>

      {selectedPaymentMethod && (
        <p>선택된 결제 수단: {selectedPaymentMethod}</p>
      )}
    </div>
  );
}

export default Paypage;
