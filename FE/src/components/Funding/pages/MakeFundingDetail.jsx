import React, { useState, useRef, forwardRef } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate 사용
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import egg from "/imgs/egg3.jpg";
import Footer from "../../UI/Footer";
import anniversaryData from "../data";

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

  const CustomInput = forwardRef(({ value, onClick }, ref) => (
    <button
      onClick={() => setShowDatePicker(true)}
      ref={ref}
      className="calendar-button common-btn h-6 bg-gray-500 text-xs"
    >
      선택
    </button>
  ));

  const navigate = useNavigate(); // useNavigate 훅 사용
  const [showDatePicker, setShowDatePicker] = useState(false);
  const ref = useRef(null); // DatePicker에 대한 ref를 생성합니다.
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
          <div className="flex flex-col items-center justify-center text-center font-cusFont3">
            <div
              id="imgSection"
              className="mb-8 flex w-2/3 items-center justify-center text-center"
            >
              <img src={egg} alt="" className="mx-auto" />
            </div>
            <div id="itemInfo">
              <p className="p-2 font-cusFont2 text-xl"> 에어팟 맥스</p>
              <p>760,000</p>
              <p>로 선물은 만들어 볼까요?</p>
            </div>
          </div>
        );
      case 1:
        return (
          <div className="text-md flex flex-col  justify-center">
            <div id="card-content">
              <div id="is-bestfriend" className="mb-6 flex">
                <p className="mr-4 ">친한친구에게만 공개하기</p>
                <input
                  type="checkbox"
                  name="bestFriend"
                  checked={formData.bestFriend}
                  onChange={handleInputChange}
                  className="p-4"
                />
              </div>
              <div id="funding-title" className="mb-6">
                <p>펀딩 제목</p>
                <input
                  type="text"
                  name="title"
                  value={formData.title}
                  placeholder="펀딩 제목을 입력해 주세요."
                  onChange={handleInputChange}
                  className="mt-2 h-7 w-full rounded-md border  border-gray-400 p-2 text-xs placeholder:text-xs"
                />
              </div>
              <div id="funding-detail">
                <p>펀딩 소개</p>
                <textarea
                  type="text"
                  name="intro"
                  value={formData.intro}
                  onChange={handleInputChange}
                  placeholder="펀딩에 대해 소개해 주세요."
                  className="mt-2 w-full rounded-md border  border-gray-400 p-2 text-xs placeholder:text-xs"
                />
              </div>
            </div>
          </div>
        );
      case 2:
        return (
          <div className="text-md flex flex-col  justify-center">
            <div id="card-content">
              <div id="anniversary" className="mb-6 ">
                <div className=" flex justify-between">
                  <p>기념일</p>
                  <button
                    className="common-btn h-6 bg-gray-500 text-xs"
                    onClick={() => navigate("/anniversary-list")}
                  >
                    변경
                  </button>
                </div>
                <div className="mt-2 text-xs">
                  <p>시은이 생일</p>
                  <p>2024.4.22</p>
                </div>
              </div>

              <div id="funding-date" className="mb-6">
                <div className="flex justify-between">
                  <p>펀딩 기간</p>
                  <DatePicker
                    ref={ref}
                    selected={formData.startDate}
                    onChange={handleDateChange}
                    onClickOutside={() => setShowDatePicker(false)}
                    open={showDatePicker}
                    selectsRange={true}
                    startDate={formData.startDate}
                    endDate={formData.endDate}
                    dateFormat="yyyy/MM/dd"
                    customInput={<CustomInput />}
                    className="p-2"
                  />
                </div>
                <div className="mt-2 flex w-full justify-between rounded-md border  border-gray-400 ">
                  <p className=" w-[80%] p-2 text-xs">
                    {formData.startDate && formData.endDate
                      ? `${getFormattedDate(formData.startDate)} ~ ${getFormattedDate(formData.endDate)}`
                      : "기간을 입력하세요"}
                  </p>
                </div>
              </div>

              <div className="mb-6">
                <p>최소금액</p>
                <input
                  type="text"
                  name="minMoney"
                  value={formData.minMoney}
                  placeholder="최소 금액을 입력해주세요."
                  className="mt-2 h-7 w-full rounded-md border  border-gray-400 p-2 text-xs placeholder:text-xs"
                  onChange={handleInputChange}
                />
              </div>
            </div>
          </div>
        );
      case 3:
        return (
          <div className="text-md flex flex-col  justify-center">
            <div id="card-content ">
              <div id="address" className="mb-6 ">
                <div className=" flex justify-between">
                  <p>주소</p>
                  <button
                    className="common-btn h-6 bg-gray-500 text-xs"
                    onClick={() => navigate("/address-list")}
                  >
                    선택
                  </button>
                </div>
                <div className="mt-4 h-[80px] rounded-md border border-gray-400 text-xs">
                  주소정보 보여주는 곳
                </div>
              </div>

              <div id="account" className="">
                <div className="flex justify-between">
                  <p>환불 계좌</p>
                  <button
                    className="common-btn h-6 bg-gray-500 text-xs"
                    onClick={() => navigate("/account-list")}
                  >
                    선택
                  </button>
                </div>
                <div className="mt-4 h-[50px] rounded-md border border-gray-400 text-xs">
                  계좌 정보 보여주는 곳
                </div>
              </div>
            </div>
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
      className="sub-layer font-cusFont3 "
      style={{
        background: "linear-gradient(to bottom, #E5EEFF, #FFFFFF)", // 세로 그라디언트 정의
      }}
    >
      <div
        id="makeCard"
        className="mt-20 flex w-[75%] flex-col items-center justify-start rounded-xl bg-white p-4 shadow-md"
        style={{ height: "68%" }} // makeCard의 높이 조정
      >
        <div id="card-title" className="w-full">
          <p className="mb-10 text-center font-cusFont2 text-lg">
            {contentIndex === 1
              ? "펀딩정보"
              : contentIndex === 2
                ? "펀딩 디테일 정보"
                : contentIndex === 3
                  ? "사용자 정보"
                  : contentIndex === 4
                    ? "펀딩 정보 확인"
                    : ""}
          </p>
        </div>
        <div id="contentSection" className="w-full overflow-auto">
          {renderContent()}
        </div>
      </div>
      <div
        id="buttonSection"
        className="absolute bottom-0 w-full justify-around pb-5"
      >
        <div className="flex justify-center space-x-4">
          {contentIndex > 0 ? (
            <>
              <button
                onClick={handlePrev}
                style={{ width: "calc(40% - 10px)" }} // 버튼 너비 조정
                className="common-btn border-cus border border-cusColor3 bg-white text-black "
              >
                이전
              </button>
              <button
                onClick={handleNext}
                style={{ width: "calc(40% - 10px)" }} // 버튼 너비 조정
                className="common-btn"
              >
                다음
              </button>
            </>
          ) : (
            <button
              onClick={handleNext}
              style={{ width: "calc(75% )" }} // 버튼 너비 조정
              className="common-btn"
            >
              다음
            </button>
          )}
        </div>
      </div>
    </div>
  );
}

export default MakeFundingDetail;
