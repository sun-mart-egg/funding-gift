import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import FundingDetailInfo from "../component/FundingDetailInfo";
import { fetchDetailFunding } from "../api/FundingAPI";
import { useParams } from "react-router-dom";
import useAttendanceStore from "../../Store/AttendanceStore";

function ParticipatePage() {
  const data = {
    progress: 70,
  };
  const navigate = useNavigate(); // useNavigate 훅을 사용합니다.
  const { fundingId } = useParams(); // URL 파라미터에서 fundingId를 가져옵니다.
  const [amount, setAmount] = useState("");
  const [error, setError] = useState("");
  const [fundingDetail, setFundingDetail] = useState(null);
  const updateUserStore = useAttendanceStore((state) => state.updateUserStore);
  // 메시지 제목에 대한 에러 상태 추가
  const [titleError, setTitleError] = useState("");
  useEffect(() => {
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
    updateUserStore("fundingId", fundingId);
  }, [fundingId]); // 의존성 배열에 fundingId 추가

  if (!fundingDetail) {
    return <div>Loading...</div>; // 데이터가 로드되기 전에 로딩 표시
  }

  const handleInputChange = (e) => {
    const value = e.target.value;
    const progress = (fundingDetail.sumPrice / fundingDetail.targetPrice) * 100;
    const max =
      fundingDetail.targetPrice - fundingDetail.targetPrice * (progress / 100);
    setAmount(value);
    if (value && Number(value) < fundingDetail.minPrice) {
      setError(`펀딩 금액은 최소 ${fundingDetail.minPrice} 이상이어야 합니다.`);
    } else if (value && Number(value) > max) {
      setError(`펀딩 금액은 최대 ${max} 이하이어야 합니다.`);
    } else {
      updateUserStore("price", value);
      setError("");
    }
  };

  const handleParticipate = () => {
    let isValid = true;

    // 메시지 제목 유효성 검사
    const progress = (fundingDetail.sumPrice / fundingDetail.targetPrice) * 100;
    const title = useAttendanceStore.getState().sendMessageTitle; // 메시지 제목 상태 가져오기
    if (!title || title.length < 4 || title.length > 20) {
      setTitleError("메시지 제목은 4글자 이상 20글자 이하이어야 합니다.");
      isValid = false; // 유효하지 않으므로 isValid를 false로 설정
    } else {
      setTitleError(""); // 에러 메시지 초기화
    }

    // 금액 유효성 검사
    if (
      !amount ||
      Number(amount) < fundingDetail.minPrice ||
      Number(amount) >
        fundingDetail.targetPrice - fundingDetail.targetPrice * (progress / 100)
    ) {
      setError("유효한 펀딩 금액을 입력해주세요.");
      isValid = false; // 유효하지 않으므로 isValid를 false로 설정
    } else {
      setError(""); // 에러 메시지 초기화
    }

    // 두 유효성 검사 모두 통과한 경우에만 navigate 실행
    if (isValid) {
      navigate("/pay", { state: { amount } }); // payPage로 이동하면서 amount 값을 state로 전달합니다.
    }
  };

  const handleMessageChange = (e) => {
    const { name, value } = e.target;
    if (name === "sendMessageTitle") {
      if (value.length < 4 || value.length > 20) {
        setTitleError("메시지 제목은 4글자 이상 20글자 이하이어야 합니다.");
      } else {
        setTitleError(""); // 에러 메시지 초기화
      }
      updateUserStore("sendMessageTitle", value);
    } else if (name === "sendMessage") {
      updateUserStore("sendMessage", value);
    }
  };

  return (
    <div className="sub-layer overflow-auto">
      <div
        id="page"
        className=" absolute top-20 flex flex-col items-center justify-center overflow-auto pb-20"
      >
        <FundingDetailInfo
          title={fundingDetail.title}
          name={fundingDetail.productName}
          detail={fundingDetail.content}
          progress={(fundingDetail.sumPrice / fundingDetail.targetPrice) * 100}
          price={fundingDetail.targetPrice}
          img={fundingDetail.productImage}
          startDate={fundingDetail.startDate}
          endDate={fundingDetail.endDate}
          fundingStatus={fundingDetail.fundingStatus}
        />
        <input
          type="number"
          placeholder="펀딩할 금액을 입력하세요."
          className="w-[80%] rounded-lg border border-gray-400 p-1"
          value={amount}
          onChange={handleInputChange}
        />
        <p className="w-[80%] pt-2 text-right text-[12px]">
          최소 {fundingDetail.minPrice} / 최대 :{" "}
          {fundingDetail.targetPrice -
            (fundingDetail.targetPrice *
              (fundingDetail.sumPrice / fundingDetail.targetPrice) *
              100) /
              100}
        </p>
        {error && <p className=" text-red-500">{error}</p>}
        <p className="w-[80%] text-left font-cusFont2 text-[18px]">
          축하메세지 보내기
        </p>
        <input
          name="sendMessageTitle"
          placeholder="메세지 제목을 입력해 주세요"
          className="mt-3 w-[80%] rounded-lg border border-gray-400 p-1"
          onChange={handleMessageChange}
        />
        {titleError && <p className="text-red-500">{titleError}</p>}{" "}
        {/* 메시지 제목 에러 표시 */}
        <textarea
          name="sendMessage"
          placeholder="친구에게 축하 메세지를 전달해 보세요."
          className="p- mt-3 h-32 w-[80%] rounded-lg border border-gray-400"
          onChange={handleMessageChange}
        ></textarea>
        <button
          onClick={handleParticipate}
          className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white"
        >
          결제하기
        </button>
      </div>
    </div>
  );
}

export default ParticipatePage;
