import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import SampleImage from "/imgs/image_coming_soon.png";

import Star from "/imgs/star.png";
import HeartEmpty from "/imgs/heart_empty.png";
import HeartFilled from "/imgs/heart_filled.png";
import Down from "/imgs/down.png";
import NoReview from "/imgs/no_review.png";

import useProductStore from "../Store/ProductStore";
import { useStore } from "../Store/MakeStore";
import useFormDataStore from "../Store/FormDataStore";
import Wishlist from "./Wishlist";

function ProductDetail() {
  const { productId } = useParams();
  const navigate = useNavigate();
  const resetStore = useStore((state) => state.reset);
  const resetProductData = useProductStore((state) => state.resetProductData);
  const resetFormData = useFormDataStore((state) => state.resetFormData);
  const [selectedOption, setSelectedOption] = useState(null);
  const token = localStorage.getItem("access-token")
  const [isWishlisted, setIsWishlisted] = useState(false);

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
      alert("옵션을 선택해주세요!");
    } else {
      resetStore(); // useStore의 상태 초기화
      resetProductData(); // useProductStore의 상태 초기화
      resetFormData(); // useFormDataStore의 상태 초기화
      navigate("/make-funding-detail", { state: sendData });
    }
  };

  const numberWithCommas = (number) => {
    return number.toLocaleString();
  };

  // 상품 상세 정보 가져오기 API
  const [product, setProduct] = useState(null);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await fetch(
          import.meta.env.VITE_BASE_URL + `/api/products/${productId}`, {
          headers: { Authorization: `Bearer ${token}` }
        }
        );
        const json = await response.json();
        setProduct(json.data); // 'data' 속성에 접근하여 상태에 저장
        setIsWishlisted(json.data.isWishlist);
      } catch (error) {
        console.error("Error fetching product:", error);
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


  const fetchReview = async () => {
    try {
      let url = import.meta.env.VITE_BASE_URL + `/api/reviews?product-id=${productId}&page=0&size=10&sort=${reviewSort}`;
      if (reviewOption !== null) {
        url += `&product-option-id=${reviewOption}`;
      }

      const response = await fetch(url, {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const json = await response.json();
      if (json && json.data && json.data.data) {
        setReviews(json.data.data);
      } else {
        console.log("No review data available");
        setReviews([]);
      }
    } catch (error) {
      console.error("Error fetching reviews:", error);
    }
  };

  useEffect(() => {
    fetchReview();
  }, [productId, reviewOption, reviewSort]);

  const [reviewOptionToggleVisible, setReviewOptionToggleVisible] =
    useState(false);

  const getOptionNameById = (optionId) => {
    // product가 존재하고 옵션이 있다면 해당 옵션의 이름을 반환합니다.
    if (product && product.options) {
      const option = product.options.find((option) => option.id === optionId);
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

  // 댓글 삭제

  const handleDeleteReview = (reviewId) => {
    fetch(import.meta.env.VITE_BASE_URL + `/api/reviews/${reviewId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}` // 인증 토큰이 필요한 경우
      }
    })
      .then(response => response.json())
      .then(data => {
        fetchReview();
      })
      .catch((error) => console.error('Error:', error));
  };

  useEffect(() => {
    // 상품 정보를 가져온 후 위시리스트 상태를 설정합니다.
    const fetchProduct = async () => {
      // ... 기존 fetchProduct 로직 ...
      if (product) {
      setIsWishlisted(product.isWishlist)};
    };
    fetchProduct();
  }, [productId]);

  const handleAddWish = async () => {
    setIsWishlisted(true);
    const requestBody = {
      productId: Number(productId) // productId를 숫자로 변환
    };

    try {
      const response = await fetch(import.meta.env.VITE_BASE_URL + '/api/wishlists', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}` // 인증 토큰이 필요한 경우
        },
        body: JSON.stringify(requestBody) // JSON 형식으로 요청 본문 구성
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const json = await response.json();
      if (json.code === 200) {
        console.log("Wishlist updated successfully");
        // 추가적인 처리 (예: 상태 업데이트 또는 사용자에게 알림 표시)
      } else {
        console.error("Error updating wishlist:", json.msg);
      }
    } catch (error) {
      console.error("Error updating wishlist:", error);
    }
  };

  const handleDeleteWish = async () => {
    setIsWishlisted(false);
    const requestBody = {
      productId: Number(productId) // productId를 숫자로 변환
    };

    try {
      const response = await fetch(import.meta.env.VITE_BASE_URL + '/api/wishlists', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}` // 인증 토큰이 필요한 경우
        },
        body: JSON.stringify(requestBody) // JSON 형식으로 요청 본문 구성
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const json = await response.json();
      if (json.code === 200) {
        console.log("Wishlist updated successfully");
        // 추가적인 처리 (예: 상태 업데이트 또는 사용자에게 알림 표시)
      } else {
        console.error("Error updating wishlist:", json.msg);
      }
    } catch (error) {
      console.error("Error updating wishlist:", error);
    }    
  };

  const toggleWishlist = async () => {
    if (isWishlisted) {
      // 위시리스트에서 삭제
      await handleDeleteWish();
    } else {
      // 위시리스트에 추가
      await handleAddWish();
    }
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
              <div className="mt-[30px] flex h-[400px] w-[100%] justify-center border-b-[2px] border-b-cusColor3">
                <img
                  src={product.imageUrl}
                  alt=""
                  className="max-h-full max-w-full"
                />
              </div>

              {/* 이미지 하단 영역 */}
              <div className="p-[15px]">
                {/* 상품 정보 */}
                <div>
                  {/* 상품 명 */}
                  <div>
                    <p className="mb-[20px] text-lg text-black">
                      {product.productName}
                    </p>
                  </div>

                  {/* 옵션 선택 */}
                  <div className="relative mb-[10px]">
                    <button
                      onClick={() =>
                        setOptionToggleVisible(!optionToggleVisible)
                      }
                      className="rounded-xl border border-cusColor3 p-2"
                    >
                      선택된 옵션:{" "}
                      {selectedOption
                        ? product.options.find(
                          (option) => option.id === selectedOption,
                        )?.name
                        : "옵션 선택 안 함"}
                    </button>

                    {optionToggleVisible && (
                      <div className="absolute left-0 z-10 mt-1 w-52 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5">
                        <div
                          className="py-1"
                          role="menu"
                          aria-orientation="vertical"
                          aria-labelledby="options-menu"
                        >
                          {product.options.map((option) => (
                            <button
                              key={option.id}
                              onClick={() => handleOptionChange(option.id)}
                              className={`${selectedOption === option.id ? "bg-cusColor3 text-white" : ""} block w-full px-4 py-2 text-left text-sm`}
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
                    <span className="text-base-bold ml-1">
                      {product.reviewAvg}
                    </span>
                  </div>

                  {/* 가격 정보 */}
                  <div className="text-base-bold">
                    {numberWithCommas(product.price)}원
                  </div>

                  {/* 상품 정보 */}
                  <p className="text-bold mb-[5px] mt-[10px] h-[100px] w-full rounded-xl border-[1.5px] border-cusColor3 p-3 text-sm">
                    {product.description}
                  </p>
                </div>

                <br />

                {/*후기 수, 정렬순서 토글, 필터 */}
                <div className="mt-2 flex h-[50px] w-[100%] items-center justify-between">
                  <div className="w-[26%]">
                    <span className="text-base">
                      선물 후기 ({product.reviewCnt})
                    </span>
                  </div>
                  <div className="relative ml-[20%] flex w-[25%] text-right">
                    <button
                      className="flex w-[100%] justify-end rounded-md text-base"
                      onClick={() =>
                        setReviewOptionToggleVisible(!reviewOptionToggleVisible)
                      }
                    >
                      {getOptionNameById(reviewOption) || "전체"}
                      <img
                        src={Down}
                        alt=""
                        className="ml-[5px] mt-[5px] h-[14px] w-[15px] invert"
                      />
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
                  <div className="relative flex w-[28%] justify-center text-right">
                    <button
                      className="flex w-[100%] justify-center  rounded-md text-right text-base"
                      onClick={toggleListVisibility}
                    >
                      {selectedFilter}
                      <img
                        src={Down}
                        alt=""
                        className="ml-[5px] mt-[5px] h-[14px] w-[15px] invert"
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
                  {reviews &&
                    reviews.map((review) => (
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
                            <p className="mb-[5px]">{review.writerName}</p>
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
                          <div className="w-[20%]">
                            {review.isMe && (<div onClick={() => handleDeleteReview((review.reviewId))} className="text-red-500">삭제</div>)}
                          </div>
                        </div>

                        {/* 리뷰 이미지 */}
                        <div className="w-full flex">
                          {review.image2 && !review.image1 && (
                            <div className="my-[20px] flex w-full">
                              <div
                                className={`mx-[2.5%] h-[150px] w-[50%] rounded-md bg-gray-300`}
                              >
                                <img
                                  src={review.image2}
                                  alt="Review Image2"
                                  className="h-full w-full rounded-md object-cover"
                                />
                              </div>
                            </div>
                          )}

                          {review.image1 && !review.image2 && (
                            <div className="my-[20px] flex w-full">
                              <div
                                className={`mx-[2.5%] h-[150px] w-[50%] rounded-md bg-gray-300`}
                              >
                                <img
                                  src={review.image1}
                                  alt="Review Image1"
                                  className="h-full w-full rounded-md object-cover"
                                />
                              </div>
                            </div>
                          )}

                          {review.image1 && review.image2 && (
                            <div className="my-[20px] flex w-full">
                              <div
                                className={`mx-[2.5%] h-[150px] w-[100%] rounded-md bg-gray-300`}
                              >
                                <img
                                  src={review.image1}
                                  alt="Review Image1"
                                  className="h-full w-full rounded-md object-cover"
                                />
                              </div>
                              <div
                                className={`mx-[2.5%] h-[150px] w-[100%] rounded-md bg-gray-300`}
                              >
                                <img
                                  src={review.image2}
                                  alt="Review Image2"
                                  className="h-full w-full rounded-md object-cover"
                                />
                              </div>
                            </div>
                          )}
                        </div>


                        {/* 리뷰 내용 */}
                        <div className="ml-7-1 mb-2 h-[100px] w-[100%] rounded-md border-[2px] p-2">
                          <p className="text-xs">{review.content}</p>
                        </div>
                      </div>
                    ))}
                  {reviews.length === 0 && (
                    <div
                      className="text-md text-center font-cusFont4"
                      style={{ backgroundColor: "#FFFFF6" }}
                    >
                      <img src={NoReview} alt="" />
                      <p>
                        아직 리뷰가 없어요. 펀딩을 만들고 리뷰를 작성해보세요!
                      </p>
                    </div>
                  )}
                </div>
              </div>
            </div>
          )}
          <div className="min-h-[70px]"></div>
          {/* 하단 고정 버튼 */}
          <div className="fixed bottom-0 flex h-[50px] w-[97%] max-w-[500px] items-center justify-center bg-white">
            {product ? (
              <>
                <button
                  onClick={toggleWishlist}
                  className="absolute left-[8%] mr-[20px]"
                >
                  <img src={isWishlisted ? HeartFilled : HeartEmpty} alt="" className="w-[27px]" />
                </button>

                <button
                  className="h-[80%] w-[50%] rounded-lg bg-cusColor3 px-4 py-2 text-base font-bold text-white hover:bg-red-400"
                  onClick={handleClick}
                >
                  펀딩 만들기
                </button>
              </>
            ) : (
              <p>Loading...</p> // 로딩 표시
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductDetail;
