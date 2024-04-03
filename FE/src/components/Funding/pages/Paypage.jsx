import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import useAttendanceStore from "../../Store/AttendanceStore";
import { createAttendance } from "../api/AttendanceAPI";
import { fetchDetailFunding } from "../api/FundingAPI";
function Paypage() {
  const navigate = useNavigate(); // useNavigate 훅을 사용합니다.
  const [fundingDetail, setFundingDetail] = useState(null);
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);
  const { sendMessage, sendMessageTitle, price, fundingId } =
    useAttendanceStore();

  // 결제 수단을 선택하는 함수입니다.
  const selectPaymentMethod = (method) => {
    setSelectedPaymentMethod(method);
  };
  const [attendanceResponse, setAttendanceResponse] = useState();

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

  useEffect(() => {
    console.log("펀딩 id " + fundingId);
    const token = localStorage.getItem("access-token");
    if (token && fundingId) {
      console.log(`Fetching funding details for ID: ${fundingId}`);
      fetchDetailFunding(token, fundingId, setFundingDetail)
        .then(() => {
          console.log("Funding details fetched successfully.");
        })
        .catch((error) => {
          console.error("Error fetching funding details:", error);
        });
    }
  }, [fundingId]);

  if (!fundingDetail) {
    return <div>Loading...</div>; // 데이터가 로드되기 전에 로딩 표시
  }

  const handlePayment = async () => {
    try {
      await createAttendance(
        localStorage.getItem("access-token"),
        fundingId,
        sendMessage,
        sendMessageTitle,
        price,
        setAttendanceResponse,
      );
      requestPay();
    } catch (error) {
      console.error("결제 중 오류가 발생했습니다: ", error);
      // 오류 처리 로직을 여기에 추가할 수 있습니다.
    }
  };

  const requestPay = () => {
    const { IMP } = window;
    IMP.init("imp40448376");

    IMP.request_pay(
      {
        pg: "html5_inicis.INIpayTest",
        pay_method: "card",
        merchant_uid: attendanceResponse.orderId,
        name: attendanceResponse.itemName,
        amount: attendanceResponse.paymentPrice,
        buyer_email: attendanceResponse.buyerEmail,
        buyer_name: attendanceResponse.buyerName,
        buyer_tel: "010-1234-5678",
        buyer_addr: "서울특별시",
        buyer_postcode: "123-456",
      },
      async (rsp) => {
        try {
          const { data } = await axios.post(
            "http://localhost:8081/api/payment-info",
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("access-token")}`,
              },
              body: JSON.stringify({
                payment_uid: rsp.imp_uid, // 결제 고유번호
                attendance_id: attendanceResponse.orderId,
              }),
            },
          );
          if (rsp.paid_amount === data.response.amount) {
            alert("결제 성공");
            navigate("/participate-funding-finish"); // 결제가 성공적으로 이루어진 후 페이지 이동
          } else {
            alert("결제 금액이 안맞아용");
          }
        } catch (error) {
          console.error("Error while verifying payment:", error);
          alert("결제를 실패했습니다.");
        }
      },
    );
  };

  if (!fundingDetail) {
    return <div>Loading...</div>; // 데이터가 로드되기 전에 로딩 표시
  }

  return (
    <div className="sub-layer flex justify-around pt-20 font-cusFont3">
      <div className="flex  w-[80%] flex-col justify-start">
        <p className=" pb-2 font-cusFont2 text-[20px]">펀딩 참여 정보</p>
        <div
          id="fundingInfo"
          className="flex h-[180px] items-center justify-center rounded-md border border-gray-400 pt-1"
        >
          <div
            id="left"
            className="flex w-[50%] items-center justify-center p-3"
          >
            <img
              src={fundingDetail.productImage}
              alt=""
              className="rounded-md"
            />
          </div>
          <div
            id="right"
            className="m-2 flex w-[50%] flex-col items-start justify-start text-xs"
          >
            <p className="pb-1">펀딩명 : {fundingDetail.title}</p>
            <p className="pb-1">상품명 : {fundingDetail.productName}</p>
            <p className="pb-4">수령자 : {fundingDetail.consumerName}</p>

            <p className="pb-1">{sendMessageTitle}</p>

            <p className="pb-1">{sendMessage}</p>
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
          {Number(price).toLocaleString()} 원
        </p>
      </div>
      <button
        onClick={handlePayment}
        className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white"
      >
        결제하기
      </button>
    </div>
  );
}

export default Paypage;
