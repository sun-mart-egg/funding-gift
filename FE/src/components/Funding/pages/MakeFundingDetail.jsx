import React, { useState, useRef, forwardRef, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate 사용
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useStore } from "../../Store/MakeStore";
import useFormDataStore from "../../Store/FormDataStore";
import { createFunding } from "../api/FundingAPI";
import { useLocation } from "react-router-dom";
import useProductStore from "../../Store/ProductStore";
import useUserStore from "../../Store/UserStore";
import { getAnniversaryList } from "../api/Anniversary";

function MakeFundingDetail() {
  const [accessToken, setAccessToken] = useState("");
  const navigate = useNavigate(); // useNavigate 훅 사용
  const location = useLocation(); // useLocation 훅 사용
  const [showDatePicker, setShowDatePicker] = useState(false);
  const ref = useRef(null); // DatePicker에 대한 ref를 생성합니다.
  const { formData, updateFormData } = useFormDataStore();
  const { product, setProduct } = useProductStore();
  const { option, setOption } = useProductStore();
  const [anniversaryCategory, setAnniversaryCategory] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");

  const {
    contentIndex,
    setContentIndex,
    selectedAnniversary,
    selectedAddress,
    selectedAccount,
  } = useStore(); // Zustand에서 상태를 가져옵니다.

  // 기념일 카테고리 변경 처리
  const handleCategoryChange = (event) => {
    const newCategory = event.target.value;
    setSelectedCategory(newCategory);
    updateFormData("anniversaryCategoryId", newCategory); // Zustand 스토어 업데이트
  };

  const handleAnniversaryChange = (e) => {
    const newAnniversaryDate = e.target.value;
    updateFormData("anniversaryDate", newAnniversaryDate); // Zustand 상태 업데이트
  };

  // 드롭다운 렌더링 함수
  const renderCategoryDropdown = () => (
    <select
      value={selectedCategory}
      onChange={handleCategoryChange}
      className="mt-2 h-8 w-full rounded-md border border-gray-400 p-2 text-xs"
    >
      <option value="">기념일을 선택하세요</option>
      {anniversaryCategory.map((category, index) => (
        <option key={index} value={category.anniversaryCategoryId}>
          {category.anniversaryCategoryName}
        </option>
      ))}
    </select>
  );
  const { name } = useUserStore();

  useEffect(() => {
    if (name) {
      console.log("이름 : ", name);
      updateFormData("name", name);
    }
  }, []);

  useEffect(() => {
    console.log("기념일 카테고리? : ", anniversaryCategory[0]);
  }, [anniversaryCategory]);

  const CustomInput = forwardRef(({ value, onClick }, ref) => (
    <button
      onClick={() => setShowDatePicker(true)}
      ref={ref}
      className="calendar-button common-btn h-6 bg-gray-500 text-xs"
    >
      선택
    </button>
  ));

  // product 상태가 변경될 때마다 실행되는
  useEffect(() => {
    if (product) {
      console.log("product : " + product.imageUrl);
      console.log("option : " + option);

      updateFormData("targetPrice", product.price);
      updateFormData("productId", product.productId);
      updateFormData("productOptionId", option);
    }
  }, [product]);

  useEffect(() => {
    const token = localStorage.getItem("access-token");
    setAccessToken(token);

    console.log(location.state);

    const fetchProduct = async () => {
      try {
        const response = await fetch(
          `https://j10d201.p.ssafy.io/api/products/${location.state.params}`,
        );
        const json = await response.json();
        setProduct(json.data); // 'data' 속성에 접근하여 상태에 저장
      } catch (error) {
        console.error("Error fetching product:", error);
      }
    };

    getAnniversaryList(token, setAnniversaryCategory);

    // product가 null이면 fetchProduct 실행
    if (!product && location.state.params) {
      console.log("목록 가져오는거 실행했습니다.");
      setOption(location.state.option);
      fetchProduct();
    }

    if (selectedAnniversary) {
      updateFormData("anniversaryDate", selectedAnniversary.anniversaryDate);
    }
    if (selectedAccount) {
      updateFormData("accountBank", selectedAccount.accountBank);
      updateFormData("accountNo", selectedAccount.accountNo);
    }
    if (selectedAddress) {
      updateFormData("phoneNumber", selectedAddress.phoneNumber);
      updateFormData("defaultAddr", selectedAddress.defaultAddr);
      updateFormData("detailAddr", selectedAddress.detailAddr);
      updateFormData("zipCode", selectedAddress.zipCode);
    }
  }, [selectedAnniversary, selectedAccount, updateFormData]);

  // 다음 컨텐츠를 보여주는 함수
  const handleNext = async () => {
    if (contentIndex < 4) {
      setContentIndex(contentIndex + 1);
    } else {
      try {
        console.log(accessToken);
        const result = await createFunding(formData, accessToken);
        console.log("Response from the server:", result);
        navigate("/make-funding-finish");
      } catch (error) {
        console.error("Failed to create funding:", error);
      }
    }
  };

  // 이전 컨텐츠를 보여주는 함수
  const handlePrev = () => {
    if (contentIndex > 0) {
      setContentIndex(contentIndex - 1); // 상태 업데이트
    } else {
      setContentIndex(0);
    }
  };

  // 폼 데이터를 처리하는 함수
  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    const updatedValue =
      name === "minPrice"
        ? parseInt(value, 10) || 0
        : type === "checkbox"
          ? checked
          : value;
    updateFormData(name, updatedValue);
  };

  // 날짜 범위 변경 핸들러
  const handleDateChange = (dates) => {
    const [start, end] = dates;
    updateFormData("startDate", start);
    updateFormData("endDate", end);
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
    if (!product) {
      // product가 로드되지 않았을 때 표시할 내용
      return <div>상품 정보를 불러오는 중...</div>;
    }
    switch (contentIndex) {
      case 0:
        return (
          <div className=" flex flex-col items-center justify-center text-center font-cusFont3 focus:border-cusColor3">
            <div
              id="imgSection"
              className="mb-8 flex w-2/3 items-center justify-center text-center"
            >
              <img src={product.imageUrl} alt="" className="w-24" />
            </div>
            <div id="itemInfo">
              <p className="p-2 font-cusFont2 text-xl">{product.productName}</p>
              <p> {product.price} 원</p>

              <p className="pt-2"> 선물은 만들어 볼까요?</p>
            </div>
          </div>
        );
      case 1:
        return (
          <div className="text-md flex flex-col  justify-center ">
            <div id="card-content">
              <div id="is-isPrivate" className="mb-6 flex">
                <p className="mr-4 ">친한친구에게만 공개하기</p>
                <input
                  type="checkbox"
                  name="isPrivate"
                  checked={formData.isPrivate}
                  onChange={handleInputChange}
                  className="p-4"
                />
              </div>
              <div id="funding-title" className="mb-6">
                <p>펀딩 제목</p>
                <input
                  type="text"
                  name="title"
                  value={formData.title || ""}
                  placeholder="펀딩 제목을 입력해 주세요."
                  onChange={handleInputChange}
                  className="mt-2 h-7 w-full rounded-md border  border-gray-400 p-2 text-xs placeholder:text-xs"
                />
              </div>
              <div id="funding-detail">
                <p>펀딩 소개</p>
                <textarea
                  type="text"
                  name="content"
                  value={formData.content || ""}
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
              <div id="anniversaryDate" className="mb-6 ">
                <div className=" flex-col justify-between">
                  <p>기념일</p>
                  {renderCategoryDropdown()}
                  <input
                    type="date"
                    name="anniversaryDate"
                    value={formData.anniversaryDate || ""}
                    onChange={handleAnniversaryChange} // 이벤트 핸들러 설정
                    className="mt-1 h-8 w-full rounded-md border border-[#9B9B9B] px-2 text-xs focus:border-cusColor3 focus:outline-none"
                  />
                </div>
                <div className="mt-2 text-xs">
                  {selectedAnniversary && (
                    <div>
                      <div className="flex">
                        <p className="mb-1 mr-1">{selectedAnniversary.name}</p>
                        <p>{selectedAnniversary.anniversaryName}</p>
                      </div>
                      <p>{selectedAnniversary.anniversaryDate}</p>
                    </div>
                  )}
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
                  type="number"
                  name="minPrice"
                  value={formData.minPrice || ""}
                  placeholder="최소 금액을 입력해주세요."
                  className="mt-2 h-8 w-full rounded-md border border-gray-400 p-2 text-xs placeholder:text-xs"
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
                  {selectedAddress == null ? (
                    "주소를 선택해 주세요"
                  ) : (
                    <div>
                      <div className="flex">
                        <p className="mb-1 mr-1">{selectedAddress.name}</p>
                        <p>{selectedAddress.nickname}</p>
                      </div>
                      <p>{selectedAddress.phoneNumber}</p>
                      <div className="flex">
                        <p className="mr-1">{selectedAddress.defaultAddr}</p>
                        <p className="mr-1">{selectedAddress.detailAddr}</p>
                        <p className="mr-1">{selectedAddress.zipCode}</p>
                      </div>
                    </div>
                  )}
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
                  {selectedAccount == null ? (
                    "계좌를 선택해 주세요"
                  ) : (
                    <div>
                      <p>{selectedAccount.name}</p>
                      <div className="flex">
                        <p className="mb-1 mr-1">
                          {selectedAccount.accountBank}
                        </p>
                        <p>{selectedAccount.accountNo}</p>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            </div>
          </div>
        );

      case 4:
        console.log(formData);

        return (
          <div className="flex flex-col items-center justify-center text-[12px]">
            <img src={product.imageUrl} alt="" className="mb-4 w-[50%]" />
            <p className="mb-1">{product.productName}</p>
            <p className="mb-1">
              {formData.isPrivate
                ? "친한 친구에게만 공개하기"
                : "모두에게 공개하기"}
            </p>
            <p className="mb-1">{formData.title}</p>
            <p className="mb-1">{formData.content}</p>
            <p className="mb-1">{formData.anniversaryDate}</p>
            <p className="mb-1">
              {getFormattedDate(formData.startDate)} ~{" "}
              {getFormattedDate(formData.endDate)}
            </p>

            <p className="mb-1">{formData.minPrice}</p>

            <div className="mb-1 flex">
              <p className="mr-1">{formData.defaultAddr} </p>
              <p className="mr-1">{formData.detailAddr} </p>
              <p className="mr-1">{formData.zipCode} </p>
            </div>
            <p className="mb-1">{formData.accountBank}</p>
            <p className="mb-1">{formData.accountNo}</p>
          </div>
        );
    }
  };
  return (
    <div
      className="sub-layer font-cusFont3  "
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
                style={{ width: "calc(45%)" }} // 버튼 너비 조정
                className="common-btn border-cus h-[45px] border border-cusColor3 bg-white text-black "
              >
                이전
              </button>
              <button
                onClick={handleNext}
                style={{ width: "calc(45%)" }} // 버튼 너비 조정
                className=" common-btn h-[45px]"
              >
                다음
              </button>
            </>
          ) : (
            <button
              onClick={handleNext}
              style={{ width: "calc(75% )" }} // 버튼 너비 조정
              className="common-btn h-[45px]"
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
