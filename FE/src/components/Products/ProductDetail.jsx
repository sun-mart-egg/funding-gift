import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useStore } from "../Store/MakeStore";
import SampleImage from "/imgs/image_coming_soon.png";

import Star from "/imgs/star.png";
import Heart from "/imgs/heart.png";
import Down from "/imgs/down.png";
<<<<<<< HEAD
import SampleReview from "/imgs/samplereview.jpg";
import ActivatedSmile from "/imgs/smile_activated.png";
import DeactivatedSmile from "/imgs/smile_deactivated.png";
import useFormDataStore from "../Store/FormDataStore";
=======
import NoReview from "/imgs/no_review.png";
>>>>>>> fe

function ProductDetail() {
  const { productId } = useParams();
  const navigate = useNavigate();
  const reset = useStore((state) => state.reset); // reset 함수를 가져옵니다.
<<<<<<< HEAD
  const resetFormData = useFormDataStore((state) => state.resetFormData);

  const resetAll = () => {
    reset(); // MakeStore의 상태를 초기화
    resetFormData(); // FormDataStore의 상태를 초기화
  };

  const handleClick = () => {
    resetAll(); // 상태 초기화

    navigate("/make-funding-detail");
=======
  const [selectedOption, setSelectedOption] = useState(null);

  // 옵션 토글 가시성 상태
  const [optionToggleVisible, setOptionToggleVisible] = useState(false);

  // 선택된 옵션 변경 핸들러
  const handleOptionChange = (optionId) => {
    setSelectedOption(optionId);
    setOptionToggleVisible(false); // 토글 닫기
  };

  const sendData = {
    params: productId,
    option: selectedOption,
  };

  const handleClick = () => {
    if (selectedOption === null) {
      alert('옵션을 선택해주세요!');
    } else {
      reset(); // 상태 초기화
      navigate("/make-funding-detail", { state: sendData });
    }
>>>>>>> fe
  };

  const numberWithCommas = (number) => {
    return number.toLocaleString();
  };

  // 상품 상세 정보 가져오기 API
  const [product, setProduct] = useState(null);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await fetch(`https://j10d201.p.ssafy.io/api/products/${productId}`);
        const json = await response.json();
        setProduct(json.data); // 'data' 속성에 접근하여 상태에 저장
      } catch (error) {
        console.error('Error fetching product:', error);
      }
    };

    fetchProduct();
  }, [productId]);

  const [toggleListVisible, setToggleListVisible] = useState(false);
  const [selectedFilter, setSelectedFilter] = useState("최신 순");

  const toggleListVisibility = () => {
    setToggleListVisible(!toggleListVisible);
  };

  const handleFilterChange = (filter) => {
    setSelectedFilter(filter);

    // Set reviewSort state based on selected filter
    switch (filter) {
      case "최신 순":
        setReviewSort(0); // Assuming 0 represents '최신 순'
        break;
      case "별점 높은 순":
        setReviewSort(1); // Assuming 1 represents '별점 높은 순'
        break;
      case "별점 낮은 순":
        setReviewSort(2); // Assuming 2 represents '별점 낮은 순'
        break;
      default:
        setReviewSort(0); // Default case if none of the above
    }

    setToggleListVisible(false); // Close the filter menu
  };

  // 댓글 정보 가져오기 API
  const [reviews, setReviews] = useState([]);
  const [reviewOption, setReviewOption] = useState("");
  const [reviewSort, setReviewSort] = useState(0);

  useEffect(() => {
    const fetchReview = async () => {
      try {
        let url = `https://j10d201.p.ssafy.io/api/reviews?product-id=${productId}&page=0&size=10&sort=${reviewSort}`;
        if (reviewOption !== null) {
          url += `&product-option-id=${reviewOption}`;
        }
  
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const json = await response.json();
        if (json && json.data && json.data.data) {
          setReviews(json.data.data);
        } else {
          console.log("No review data available");
          setReviews([]);
        }
      } catch (error) {
        console.error('Error fetching reviews:', error);
      }
    };
  
    fetchReview();
  }, [productId, reviewOption, reviewSort]);

  const [reviewOptionToggleVisible, setReviewOptionToggleVisible] = useState(false);

  const getOptionNameById = (optionId) => {
    // product가 존재하고 옵션이 있다면 해당 옵션의 이름을 반환합니다.
    if (product && product.options) {
      const option = product.options.find(option => option.id === optionId);
      return option ? option.name : "";
    }
    return "";
  };

  const handleReviewOptionChange = (optionId) => {
    // '전체' 옵션을 클릭했을 때 reviewOption을 null로 설정하여 모든 리뷰를 가져오도록 합니다.
    if (optionId === "전체") {
      setReviewOption(null);
    } else {
      setReviewOption(optionId);
    }
    setReviewOptionToggleVisible(false); // 토글 닫기
  };


  return (
    <div className="sub-layer">
      {/* 배경 */}
      <div className="flex h-screen items-center justify-center overflow-y-auto overflow-x-hidden font-cusFont2">
        {/* 화면 - 패딩은 모바일 기준이므로 요소들이 너무 구석에 가지 않도록 설정 */}
        <div className="flex-start flex h-screen min-h-[600px] max-w-[500px] flex-col items-center justify-start overflow-y-auto overflow-x-hidden bg-white">
          {/* 본문 자리 */}
          {product && (
            <div className="mt-[70px]">
              {/* 이미지 들어갈 영역 */}
              <div className="flex h-[400px] w-[100%] justify-center mt-[30px] border-b-[2px] border-b-cusColor3">
                <img src={product.imageUrl} alt="" className="max-h-full max-w-full" />
              </div>

              {/* 이미지 하단 영역 */}
              <div className="p-[15px]">
                {/* 상품 정보 */}
                <div>
                  {/* 상품 명 */}
                  <div>
                    <p className="text-black mb-[20px] text-lg">
                      {product.productName}
                    </p>
                  </div>


                  {/* 옵션 선택 */}
                  <div className="relative mb-[10px]">
                    <button
                      onClick={() => setOptionToggleVisible(!optionToggleVisible)}
                      className="p-2 border border-cusColor3 rounded-xl"
                    >
                      선택된 옵션: {selectedOption ? product.options.find(option => option.id === selectedOption)?.name : "옵션 선택 안 함"}
                    </button>

                    {optionToggleVisible && (
                      <div className="absolute z-10 mt-1 left-0 w-52 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5">
                        <div className="py-1" role="menu" aria-orientation="vertical" aria-labelledby="options-menu">
                          {product.options.map((option) => (
                            <button
                              key={option.id}
                              onClick={() => handleOptionChange(option.id)}
                              className={`${selectedOption === option.id ? "bg-cusColor3 text-white" : ""} block w-full text-left px-4 py-2 text-sm`}
                            >
                              {option.name}
                            </button>
                          ))}
                        </div>
                      </div>
                    )}
                  </div>


                  {/* 별점 정보 */}
                  <div className="mb-[5px] flex">
                    <img
                      src={Star}
                      alt=""
                      className="aspect-auto w-[4%] object-contain"
                    />
                    <span className="text-base-bold ml-1">{product.reviewAvg}</span>
                  </div>

                  {/* 가격 정보 */}
                  <div className="text-base-bold">{numberWithCommas(product.price)}원</div>

                  {/* 상품 정보 */}
                  <p className="text-bold p-3 w-full h-[100px] border-[1.5px] border-cusColor3 mb-[5px] mt-[10px] text-sm rounded-xl">
                    {product.description}
                  </p>

                </div>



                <br />

                {/*후기 수, 정렬순서 토글, 필터 */}
                <div className="mt-2 flex h-[50px] w-[100%] items-center justify-between">
                  <div>
                    <span className="text-base">선물 후기 ({product.reviewCnt})</span>
                  </div>
                  <div className="ml-[25%] flex relative w-[25%] text-right">
                    <button
                      className="flex w-[100%] justify-end rounded-md text-base"
                      onClick={() => setReviewOptionToggleVisible(!reviewOptionToggleVisible)}
                    >
                      {getOptionNameById(reviewOption) || "전체"}
                      <img src={Down} alt="" className="mt-[5px] ml-[5px] h-4 w-4 invert" />
                    </button>
                    {reviewOptionToggleVisible && (
                      <div className="absolute right-0 top-full w-[100%] rounded-md border border-gray-300 bg-white p-2 px-[3%] text-center text-xs">
                        {/* Option for '전체' */}
                        <p
                          className={`${reviewOption === "" ? "bg-cusColor3 text-white" : ""} mb-1`}
                          onClick={() => handleReviewOptionChange("")}
                        >
                          전체
                        </p>
                        {/* List of Product Options */}
                        {product.options.map((option) => (
                          <p
                            key={option.id}
                            className={`${reviewOption === option.id ? "bg-cusColor3 text-white" : ""} mb-1`}
                            onClick={() => handleReviewOptionChange(option.id)}
                          >
                            {option.name}
                          </p>
                        ))}
                      </div>
                    )}
                  </div>
                  <div className="relative w-[25%] text-right flex justify-center">
                    <button
                      className="flex w-[100%] justify-center  rounded-md text-right text-base"
                      onClick={toggleListVisibility}
                    >
                      {selectedFilter}
                      <img
                        src={Down}
                        alt=""
                        className="mt-[5px] ml-[5px] h-[14px] w-[15%] invert"
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
                  {reviews && reviews.map((review) => (
                    <div
                      key={review.reviewId}
                      className="mt-4 flex w-full flex-col items-start justify-between"
                    >
                      {/* 리뷰 프로필 이미지, 별점, 이름 */}
                      <div className="mb-2 flex items-center">
                        <div className="mr-[3%] aspect-square w-[18%] overflow-hidden rounded-full bg-black">
                          <img
                            src={review.writerProfile}
                            alt={review.writerName}
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
                            <span className="ml-1">{review.star} / 5</span>
                          </div>
                          <div>{review.name}</div>
                        </div>
                      </div>

                      {/* 리뷰 이미지 */}
                      {review.image1 && (
                        <div className="my-[20px] flex w-full">
                          <div
                            className={`mx-[2.5%] h-[150px] w-[45%] rounded-md bg-gray-300`}
                          >
                            <img
                              src={review.image1}
                              alt="Review Image1"
                              className="h-full w-full rounded-md object-cover"
                            />
                          </div>
                        </div>
                      )}

                      {review.image2 && (
                        <div className="my-[20px] flex w-full">
                          <div
                            className={`mx-[2.5%] h-[150px] w-[45%] rounded-md bg-gray-300`}
                          >
                            <img
                              src={review.image2}
                              alt="Review Image2"
                              className="h-full w-full rounded-md object-cover"
                            />
                          </div>
                        </div>
                      )}

                      {/* 리뷰 내용 */}
                      <div className="ml-7-1 mb-2 h-[100px] w-[100%] rounded-md border-[2px] p-2">
                        <p className="text-xs">{review.content}</p>
                      </div>

                    </div>
                  ))}
                  {
                    reviews.length === 0 && (
                      <div className="font-cusFont4 text-center text-md" style={{ backgroundColor: '#FFFFF6' }}>
                        <img src={NoReview} alt="" />
                        <p>아직 리뷰가 없어요. 펀딩을 만들고 리뷰를 작성해보세요!</p>
                      </div>
                    )
                  }
                </div>
              </div>
            </div>
          )}
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
