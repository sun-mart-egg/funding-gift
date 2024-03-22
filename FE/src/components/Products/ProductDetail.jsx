import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import SampleImage from "../../../public/imgs/product_sample.png";

import Star from "../../../public/imgs/star.png";
import Heart from "../../../public/imgs/heart.png";
import Down from "../../../public/imgs/down.png";
import SampleReview from "../../../public/imgs/samplereview.jpg";
import ActivatedSmile from "../../../public/imgs/smile_activated.png";
import DeactivatedSmile from "../../../public/imgs/smile_deactivated.png";

function ProductDetail() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/make-funding-detail");
  };

  const [toggleListVisible, setToggleListVisible] = useState(false);
  const [selectedFilter, setSelectedFilter] = useState("최신 순");
  const [smileStates, setSmileStates] = useState({});

  const toggleListVisibility = () => {
    setToggleListVisible(!toggleListVisible);
  };

  const handleFilterChange = (filter) => {
    setSelectedFilter(filter);
    setToggleListVisible(false); // 필터를 선택하면 목록을 닫음
  };

  const handleSmileClick = (id) => {
    setSmileStates((prevState) => ({
      ...prevState,
      [id]: !prevState[id], // 해당 리뷰의 상태를 토글
    }));
  };

  const reviews = [
    {
      id: 1,
      profileImage: "../../../public/imgs/egg3.jpg",
      rating: 1,
      name: "이민수",
      images: [SampleReview],
      content: "선물을 받았는데 벽돌이 왔어요 ㅠㅠ",
      helpfulCount: 12,
    },
    {
      id: 2,
      profileImage: "../../../public/imgs/basic.jpg",
      rating: 5,
      name: "홍보2",
      images: [],
      content:
        "너무 좋아요. 받았는데 최신 제품이 왔어요. 모두 꼭 구입하세요. 이 글을 복사 후 붙여넣기 해주세요.(이 문장은 지우고 올려주세요)",
      helpfulCount: 1,
    },
    {
      id: 3,
      profileImage: "../../../public/imgs/basic.jpg",
      rating: 5,
      name: "홍보1",
      images: [],
      content:
        "너무 좋아요. 받았는데 최신 제품이 왔어요. 모두 꼭 구입하세요. 이 글을 복사 후 붙여넣기 해주세요.(이 문장은 지우고 올려주세요)",
      helpfulCount: 1,
    },
  ];

  return (
    <div className="main-layer">
      {/* 배경 임시로 회색으로 설정 -> 나중에 이미지 혹은 단일 색으로 채울 것(웹 기준) */}
      <div className="flex h-screen items-center justify-center overflow-y-auto overflow-x-hidden bg-gray-100 font-cusFont2">
        {/* 화면 - 패딩은 모바일 기준이므로 요소들이 너무 구석에 가지 않도록 설정 */}
        <div className="flex-start flex h-screen min-h-[600px] max-w-[500px] flex-col items-center justify-start overflow-y-auto overflow-x-hidden bg-white">
          {/* 상단바 자리 (브랜드 버튼) */}
          <div></div>

          {/* 본문 자리 */}
          <div className="mt-[70px]">
            {/* 이미지 들어갈 영역 */}
            <div className="flex h-[400px] w-[100%] justify-center border-b-[2px] border-b-cusColor3">
              <img src={SampleImage} alt="" className="max-h-full max-w-full" />
            </div>

            {/* 이미지 하단 영역 */}
            <div className="p-[15px]">
              {/* 상품 정보 */}
              <div>
                {/* 상품 명 */}
                <p className="text-bold mb-[5px] text-lg">
                  상품 명이 들어갈 자리입니다.
                </p>

                {/* 별점 정보 */}
                <div className="mb-[5px] flex">
                  <img
                    src={Star}
                    alt=""
                    className="aspect-auto w-[4%] object-contain"
                  />
                  <span className="text-base-bold ml-1">3.7 (3)</span>
                </div>

                {/* 가격 정보 */}
                <div className="text-base-bold">760,000원</div>
              </div>

              <br />

              {/*후기 수, 정렬순서 토글, 필터 */}
              <div className="mt-2 flex h-[50px] w-[100%] items-center justify-between">
                <div>
                  <span className="text-base">선물 후기 (3)</span>
                </div>
                <div className="relative w-[40%] text-right">
                  <button
                    className="flex w-[100%] items-center justify-end  rounded-md text-right text-base"
                    onClick={toggleListVisibility}
                  >
                    {selectedFilter}
                    <img
                      src={Down}
                      alt=""
                      className="ml-[5px] h-[14px] w-[10%] invert"
                    />
                  </button>
                  {toggleListVisible && (
                    <div className="absolute right-0 top-full w-[120%] rounded-md border border-gray-300 bg-white p-2 px-[3%] text-center text-xs">
                      <p
                        className={`${selectedFilter === "최신 순" ? "bg-cusColor3 text-white" : ""} mb-1`}
                        onClick={() => handleFilterChange("최신 순")}
                      >
                        최신 순
                      </p>
                      <p
                        className={`${selectedFilter === "추천 순" ? "bg-cusColor3 text-white" : ""} mb-1`}
                        onClick={() => handleFilterChange("추천 순")}
                      >
                        추천 순
                      </p>
                      <p
                        className={`${selectedFilter === "별점 높은 순" ? "bg-cusColor3 text-white" : ""} mb-1`}
                        onClick={() => handleFilterChange("별점 높은 순")}
                      >
                        별점 높은 순
                      </p>
                      <p
                        className={`${selectedFilter === "별점 낮은 순" ? "bg-cusColor3 text-white" : ""}`}
                        onClick={() => handleFilterChange("별점 낮은 순")}
                      >
                        별점 낮은 순
                      </p>
                    </div>
                  )}
                </div>
              </div>

              {/* 리뷰 리스트 */}
              <div className="mt-[20px]">
                {reviews.map((review) => (
                  <div
                    key={review.id}
                    className="mt-4 flex w-full flex-col items-start justify-between"
                  >
                    {/* 리뷰 프로필 이미지, 별점, 이름 */}
                    <div className="mb-2 flex items-center">
                      <div className="mr-[3%] aspect-square w-[18%] overflow-hidden rounded-full bg-black">
                        <img
                          src={review.profileImage}
                          alt={review.name}
                          className="h-full w-full rounded-full object-cover"
                        />
                      </div>
                      <div className="w-[100%]">
                        <div className="flex items-center">
                          <img
                            src={Star}
                            alt=""
                            className="aspect-auto w-[7%] object-contain pb-1"
                          />
                          <span className="ml-1">{review.rating} / 5</span>
                        </div>
                        <div>{review.name}</div>
                      </div>
                    </div>

                    {/* 리뷰 이미지 */}
                    {review.images.length > 0 && (
                      <div className="my-[20px] flex w-full">
                        {review.images.map((image, index) => (
                          <div
                            key={index}
                            className={`mx-[2.5%] h-[150px] w-[45%] rounded-md bg-gray-300`}
                          >
                            <img
                              src={image}
                              alt={`Review Image ${index + 1}`}
                              className="h-full w-full rounded-md object-cover"
                            />
                          </div>
                        ))}
                      </div>
                    )}

                    {/* 리뷰 내용 */}
                    <div className="ml-7-1 mb-2 h-[100px] w-[100%] rounded-md border-[2px] p-2">
                      <p className="text-xs">{review.content}</p>
                    </div>

                    {/* 도움돼요 버튼 */}
                    <div className="ml-auto flex w-full justify-end text-xs">
                      <button
                        className={`flex h-[40px] w-[30%] items-center justify-center rounded-md border-[3px] ${smileStates[review.id] ? "border-cusColor3 text-cusColor3" : ""}`}
                        onClick={() => handleSmileClick(review.id)}
                      >
                        <img
                          className="mr-1 mt-[8px] w-[25%]"
                          src={
                            smileStates[review.id]
                              ? ActivatedSmile
                              : DeactivatedSmile
                          }
                          alt="Smile"
                        />
                        <p
                          className={`${smileStates[review.id] ? "text-cusColor3" : "text-gray-400"} text-xs`}
                        >
                          도움돼요 {review.helpfulCount}
                        </p>
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
          <div className="min-h-[70px]"></div>
          {/* 하단 고정 버튼 */}
          <div className="fixed bottom-0 flex h-[50px] w-[97%] max-w-[500px] items-center justify-center bg-white">
            <button className="absolute left-[8%] mr-[20px]">
              <img src={Heart} alt="" className="w-[27px]" />
            </button>
            <button
              className="h-[80%]  w-[50%] rounded-lg bg-cusColor3 px-4 py-2 text-base font-bold text-white hover:bg-red-400"
              onClick={handleClick}
            >
              펀딩 만들기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductDetail;
