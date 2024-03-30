import React from "react";

function NewAnniversaryPage() {
  return (
    <div className="sub-layer">
      <div className="title flex w-full items-center justify-center ">
        <p className="absolute top-20 font-cusFont5 text-[30px]">기념일 추가</p>
      </div>
      <div className="content absolute top-32 w-full p-4">
        <div className="anniversaryName mb-6">
          <p className="mb-2 font-cusFont2 text-[20px]">기념일 이름</p>
          <input
            type="text"
            placeholder="기념일의 이름을 입력하세요 ex)생일"
            className="h-[44px] w-full rounded-md border border-[#9B9B9B] px-2 placeholder:text-[14px]"
          />
        </div>
        <div className="anniversaryDate">
          <p className="mb-2 font-cusFont2 text-[20px]">기념일 날짜</p>
          <input
            type="date"
            className="h-[44px] w-full rounded-md border border-[#9B9B9B] px-2"
          />
        </div>
      </div>
      <div className="absolute bottom-0 flex w-full items-center justify-center space-x-4 pb-5">
        <button className="common-btn h-[45px] w-[75%]">기념일 추가하기</button>
      </div>
    </div>
  );
}

export default NewAnniversaryPage;
