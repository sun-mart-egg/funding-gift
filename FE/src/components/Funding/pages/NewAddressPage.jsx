import React from "react";

function NewAddressPage() {
  return (
    <div className="sub-layer font-cusFont3">
      <div className="title flex w-full items-center justify-center ">
        <p className="absolute top-20 font-cusFont5 text-[30px]">배송지 추가</p>
      </div>
      <div className="content absolute top-32 w-full p-4 font-cusFont3">
        <div className="addressName mb-6">
          <p className="mb-2 font-cusFont2 text-[18px]">배송지 이름</p>
          <input
            type="text"
            placeholder="배송지의 이름을 입력하세요 ex)본가"
            className="h-[44px] w-full rounded-md border border-[#9B9B9B] px-2 placeholder:text-[14px] focus:border-cusColor3 focus:outline-none"
          />
        </div>
        <div className="zipCode pb-6">
          <p className="mb-2 font-cusFont2 text-[18px]">우편번호</p>
          <div className="flex justify-between">
            <input
              type="text"
              placeholder="우편번호를 입력하세요"
              className="h-[44px] w-[60%] rounded-md border border-[#9B9B9B] px-2 placeholder:text-[14px] focus:border-cusColor3 focus:outline-none"
            />
            <button className="rounded-md bg-[#9B9B9B] p-2 text-white">
              우편번호 검색{" "}
            </button>
          </div>
        </div>
        <div className="address pb-6">
          <p className="mb-2 font-cusFont2 text-[18px]">주소</p>
          <div className="defaultAddress pb-2">
            <input
              type="text"
              placeholder="우편번호를 검색하세요"
              className="h-[44px] w-full rounded-md border border-[#9B9B9B] px-2 placeholder:text-[14px] focus:border-cusColor3 focus:outline-none"
            />
          </div>
          <div className="detailAddress">
            <input
              type="text"
              placeholder="상세 주소를 입력하세요"
              className="h-[44px] w-full rounded-md border border-[#9B9B9B] px-2 placeholder:text-[14px] focus:border-cusColor3 focus:outline-none"
            />
          </div>
        </div>
        <div className="isPrimaryAddress flex justify-between">
          <p>기본 배송지로 설정 하시겠습니까?</p>
          <input type="checkbox" />
        </div>
      </div>
      <div className="absolute bottom-0 flex w-full items-center justify-center space-x-4 pb-5">
        <button className="common-btn h-[45px] w-[75%]">배송지 추가하기</button>
      </div>
    </div>
  );
}

export default NewAddressPage;
