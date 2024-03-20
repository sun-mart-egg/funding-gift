import { useState } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate 사용
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

function MakeFundingDetail() {
  // 현재 보여줄 컨텐츠 인덱스를 상태로 관리
  const [contentIndex, setContentIndex] = useState(0);
  // 사용자 입력 데이터를 상태로 관리
  const [formData, setFormData] = useState({
    bestFriend: false,
    title: "",
    fundingIntro: "",
    anniversary: "",
    startDate: null,
    endDate: null,
    minMoney: "",
    address: "",
    account: "",
  });

  const navigate = useNavigate(); // useNavigate 훅 사용

  // 다음 컨텐츠를 보여주는 함수
  const handleNext = () => {
    if (contentIndex < 4) {
      setContentIndex((prevIndex) => prevIndex + 1);
    } else {
      //펀딩 만드는 api 연결 필요

      // 마지막 페이지에서 "다음" 버튼을 누르면 MakeFundingFinish로 라우트
      navigate("/make-funding-finish");
    }
  };

  // 이전 컨텐츠를 보여주는 함수
  const handlePrev = () => {
    setContentIndex((prevIndex) => (prevIndex > 0 ? prevIndex - 1 : 0));
  };

  // 폼 데이터를 처리하는 함수
  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  // 날짜 범위 변경 핸들러
  const handleDateChange = (dates) => {
    const [start, end] = dates;
    setFormData({ ...formData, startDate: start, endDate: end });
  };

  const getFormattedDate = (date) => {
    if (!date) return "";

    const options = {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      weekday: "long",
    };
    return date.toLocaleDateString("ko-KR", options);
  };

  // 현재 보여줄 컨텐츠를 결정하는 함수
  const renderContent = () => {
    switch (contentIndex) {
      case 0:
        return (
          <div>
            <div id="imgSection" className="mb-8 text-center">
              <img src="src\components\Funding\assets\egg3.jpg" alt="" />
            </div>
            <div id="itemInfo">
              <p> 에어팟 맥스</p>
              <p>760,000</p>
              <p>로 선물은 만들어 볼까요?</p>
            </div>
          </div>
        );
      case 1:
        return (
          <div>
            <h1>펀딩정보</h1>
            <p>친한친구 에게만 보여주기</p>
            <input
              type="checkbox"
              name="bestFriend"
              checked={formData.bestFriend}
              onChange={handleInputChange}
            />
            <p>펀딩 제목:</p>
            <input
              type="text"
              name="title"
              value={formData.title}
              onChange={handleInputChange}
            />
            <p>펀딩 소개:</p>
            <textarea
              type="text"
              name="intro"
              value={formData.intro}
              onChange={handleInputChange}
            />
          </div>
        );
      case 2:
        return (
          <div>
            <h1>펀딩 디테일 정보</h1>
            <div className="flex">
              <p>기념일</p> <button className="bg-blue-400">선택</button>
            </div>

            <input
              type="text"
              name="anniversary"
              value={formData.anniversary}
              onChange={handleInputChange}
            />
            <p>펀딩 기간:</p>
            <p>
              {getFormattedDate(formData.startDate)} ~{" "}
              {getFormattedDate(formData.endDate)}
            </p>

            <DatePicker
              selectsRange
              startDate={formData.startDate}
              endDate={formData.endDate}
              onChange={handleDateChange}
              dateFormat="yyyy/MM/dd"
            />
            <p>최소금액</p>
            <input
              type="text"
              name="minMoney"
              value={formData.minMoney}
              onChange={handleInputChange}
            />
          </div>
        );
      case 3:
        return (
          <div>
            <h1>사용자 정보</h1>
            <div className="flex">
              <p>주소</p>
              <button className="bg-blue-400">선택</button>
            </div>

            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleInputChange}
            />
            <div className="flex">
              <p>환불계좌</p>
              <button className="bg-blue-400">선택</button>
            </div>

            <input
              type="text"
              name="account"
              value={formData.account}
              onChange={handleInputChange}
            />
          </div>
        );
      case 4:
        return (
          <div>
            <img src="" alt="" />
            <p>
              {formData.bestFriend
                ? "친한 친구에게만 공개하기"
                : "모두에게 공개하기"}
            </p>
            <p>{formData.title}</p>
            <p>{formData.intro}</p>
            <p>{formData.anniversary}</p>
            <p>
              {getFormattedDate(formData.startDate)} ~{" "}
              {getFormattedDate(formData.endDate)}
            </p>

            <p>{formData.minMoney}</p>
            <p>{formData.address}</p>
            <p>{formData.account}</p>
          </div>
        );
    }
  };
  return (
    <div
      className="flex h-screen flex-col items-center justify-center"
      style={{
        background: "linear-gradient(to bottom, #E5EEFF, #FFFFFF)", // 세로 그라디언트 정의
      }}
    >
      <div
        id="makeCard"
        className="flex h-3/5 w-2/3 flex-col justify-around rounded-xl bg-white p-4"
      >
        <div id="contentSection">{renderContent()}</div>
      </div>
      <div id="buttonSection" className="flex w-full justify-around">
        {contentIndex > 0 ? (
          <>
            <button
              onClick={handlePrev}
              style={{ flexBasis: "40%" }}
              className="rounded-lg bg-cusColor3"
            >
              이전
            </button>
            <button
              onClick={handleNext}
              style={{ flexBasis: "40%" }}
              className="rounded-lg bg-cusColor4"
            >
              다음
            </button>
          </>
        ) : (
          <button
            onClick={handleNext}
            style={{ flexBasis: "40%" }}
            className="rounded-lg bg-cusColor4"
          >
            다음
          </button>
        )}
      </div>
    </div>
  );
}

export default MakeFundingDetail;
