import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import FundingDetailInfo from "../component/FundingDetailInfo";

function ParticipatePage() {
  const data = {
    frinedName: "신시은",
    title: "EGG IS MY LIFE",
    name: "계란 토스트",
    price: 760000,
    detail:
      "친구들아 안녕. 곧 내 생일인데 고오급 계란 토스트가 너무 가지고 싶어. 많은 참여 부탁해",
    progress: 70,
    min: 100,
  };

  const [amount, setAmount] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate(); // useNavigate 훅을 사용합니다.

  const handleInputChange = (e) => {
    const value = e.target.value;
    const max = data.price - data.price * (data.progress / 100);
    setAmount(value);
    if (value && Number(value) < data.min) {
      setError(`펀딩 금액은 최소 ${data.min} 이상이어야 합니다.`);
    } else if (value && Number(value) > max) {
      setError(`펀딩 금액은 최대 ${max} 이하이어야 합니다.`);
    } else {
      setError("");
    }
  };

  const handleParticipate = () => {
    // 유효성 검사 후 navigate 실행
    if (
      amount &&
      Number(amount) >= data.min &&
      Number(amount) <= data.price - data.price * (data.progress / 100)
    ) {
      navigate("/pay", { state: { amount } }); // payPage로 이동하면서 amount 값을 state로 전달합니다.
    } else {
      // 에러 처리
      setError("유효한 펀딩 금액을 입력해주세요.");
    }
  };

  return (
    <div className="sub-layer">
      <div
        id="page"
        className="flex flex-col items-center justify-center overflow-auto pb-16"
      >
        <FundingDetailInfo
          friendName={data.frinedName}
          title={data.title}
          name={data.name}
          detail={data.detail}
          progress={data.progress}
          price={data.price}
        />
      </div>
      <input
        type="number"
        placeholder="펀딩할 금액을 입력하세요."
        className="w-full"
        value={amount}
        onChange={handleInputChange}
      />
      <p>
        최소 {data.min} / 최대 :{" "}
        {data.price - (data.price * data.progress) / 100}
      </p>
      {error && <p className="text-red-500">{error}</p>}
      <p>축하메세지 보내기</p>
      <input placeholder="메세지 제목을 입력해 주세요" className="w-full" />
      <textarea
        placeholder="친구에게 축하 메세지를 전달해 보세요."
        className="w-full"
      ></textarea>
      <button
        className="fixed bottom-0 left-28 bg-blue-500 py-3 text-white"
        onClick={handleParticipate}
      >
        펀딩 참여하기
      </button>
    </div>
  );
}

export default ParticipatePage;
