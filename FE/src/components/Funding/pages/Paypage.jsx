import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom"; // useLocation을 import합니다.
import egg from "/imgs/egg3.jpg";
import axios from "axios";
function Paypage() {
  const location = useLocation(); // 현재 location 객체를 가져옵니다.
  const { amount } = location.state; // location.state에서 amount 값을 추출합니다.
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);

  // 결제 수단을 선택하는 함수입니다.
  const selectPaymentMethod = (method) => {
    setSelectedPaymentMethod(method);
  };

  useEffect(() => {
    const jquery = document.createElement("script");
    jquery.src = "http://code.jquery.com/jquery-1.12.4.min.js";
    const iamport = document.createElement("script");
    iamport.src = "http://cdn.iamport.kr/js/iamport.payment-1.1.7.js";
    document.head.appendChild(jquery);
    document.head.appendChild(iamport);
    return () => {
      document.head.removeChild(jquery);
      document.head.removeChild(iamport);
    };
  }, []);

  const requestPay = () => {
    const { IMP } = window;
    IMP.init("imp40448376");

    IMP.request_pay(
      {
        pg: "html5_inicis.INIpayTest",
        pay_method: "card",
        merchant_uid: new Date().getTime(),
        name: "테스트 상품",
        amount: 1004,
        buyer_email: "test@naver.com",
        buyer_name: "코드쿡",
        buyer_tel: "010-1234-5678",
        buyer_addr: "서울특별시",
        buyer_postcode: "123-456",
      },
      async (rsp) => {
        try {
          const { data } = await axios.post(
            "http://localhost:8080/verifyIamport/" + rsp.imp_uid,
          );
          if (rsp.paid_amount === data.response.amount) {
            alert("결제 성공");
          } else {
            alert("결제 실패");
          }
        } catch (error) {
          console.error("Error while verifying payment:", error);
          alert("결제 실패");
        }
      },
    );
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
      <button
        className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white"
        onClick={requestPay}
      >
        결제하기
      </button>

      {/* {selectedPaymentMethod && (
        <p>선택된 결제 수단: {selectedPaymentMethod}</p>
      )} */}
    </div>
  );
}

export default Paypage;
