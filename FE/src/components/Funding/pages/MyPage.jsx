import React, { useState } from "react";
import egg from "/imgs/egg3.jpg";
import { IoLogOut } from "react-icons/io5";
import { AiFillCamera } from "react-icons/ai";

function MyPage() {
  // 상태: 사용자가 현재 수정 모드에 있는지 추적
  const [isEditMode, setIsEditMode] = useState(false);
  // 상태: 사용자 정보 (여기서는 이름만 사용)
  const [userInfo, setUserInfo] = useState({
    name: "신시은",
    anniversaryDate: "1999.4.22",
    defaultAddr: "아슬란",
    detailAddr: "502호",
    zipCode: "123456",
    accountBank: "하나은행",
    accountNo: "1231231231231",
    // 추가 정보가 있다면 여기에 포함할 수 있습니다.
  });

  // 수정 버튼 클릭 시 호출될 함수
  const handleEditClick = () => {
    setIsEditMode(!isEditMode);
  };

  // 사용자 이름 변경 시 호출될 함수
  const handleNameChange = (event) => {
    setUserInfo({ ...userInfo, name: event.target.value });
  };

  return (
    <div className="sub-layer font-cusFont3">
      {isEditMode ? (
        // 수정 모드 활성화 시 보여줄 UI
        <>
          <div className="head absolute top-20 flex w-full items-center px-6">
            <div className="relative mr-4 w-[26.5%]">
              <img src={egg} alt="" className="w-full rounded-full" />
              {/* 절대 위치를 사용한 카메라 아이콘 */}
              <AiFillCamera
                className="absolute bottom-0 right-1 text-[20px] text-[#9B9B9B]"
                style={{ bottom: "-10px", right: "1px" }}
              />
            </div>
            <div className="flex w-full justify-between">
              <input
                type="text"
                value={userInfo.name}
                onChange={handleNameChange}
                className="mr-1 w-full rounded-md border border-gray-400 px-2 font-cusFont5 text-[25px]"
              />
            </div>
          </div>
          <div className="content absolute top-44 w-full px-6 font-cusFont3">
            <div className="birthday">
              <div className="sub-title">
                <p>생일</p>{" "}
                <button className="w-[25%] rounded-md bg-[#9B9B9B] text-[12px] text-white">
                  생일 선택
                </button>
              </div>
              <div className="sub-content">
                <p className="mr-1 w-full rounded-md border border-gray-400 p-3 px-2 font-cusFont3 text-[14px]">
                  {userInfo.anniversaryDate}
                </p>
              </div>
            </div>
            <div className="address">
              <div className="sub-title pt-6 ">
                <p>기본 주소</p>{" "}
                <button className="w-[25%] rounded-md bg-[#9B9B9B] text-[12px] text-white">
                  기본 주소 선택
                </button>
              </div>
              <p className="mr-1 w-full rounded-md border border-gray-400 p-3 px-2 font-cusFont3 text-[14px]">
                {userInfo.defaultAddr} {userInfo.detailAddr} {userInfo.zipCode}
              </p>
            </div>
            <div className="account">
              <div className="sub-title pt-6">
                <p>기본 계좌</p>{" "}
                <button className="w-[25%] rounded-md bg-[#9B9B9B] text-[12px] text-white">
                  기본 계좌 선택
                </button>
              </div>
              <p className="mr-1 w-full rounded-md border border-gray-400 p-3 px-2 font-cusFont3 text-[14px]">
                {userInfo.accountBank} {userInfo.accountNo}
              </p>
            </div>
          </div>
          <button className="absolute bottom-16 right-[15%] pb-3 text-[12px] text-gray-300">
            회원 탈퇴
          </button>
          <div
            id="buttonSection"
            className="absolute bottom-0 flex w-full flex-col items-center justify-around  pb-5"
          >
            <button
              onClick={handleEditClick}
              style={{ width: "calc(75% )" }} // 버튼 너비 조정
              className="common-btn"
            >
              수정 완료
            </button>
          </div>
        </>
      ) : (
        <>
          {/* head 입니다. */}
          <div className="head absolute top-20 flex w-full items-center px-6">
            <img src={egg} alt="" className="mr-4 w-[20%]  rounded-full" />
            <div className="flex w-full justify-between ">
              <p className="mr-1 px-2 font-cusFont5 text-[25px]">
                {userInfo.name}
              </p>
              <button className="flex flex-col items-center justify-center">
                <IoLogOut className="text-[25px]" />
                <p className="text-[10px]">로그아웃</p>
              </button>
            </div>
          </div>

          {/* Content 입니다. */}
          <div className="content absolute top-44 w-full px-6">
            <div className="birthday">
              <div className="sub-title">
                <p>생일</p>
              </div>
              <p className="mr-1 w-full rounded-md bg-[#EFEFEF] p-3 px-2 font-cusFont3 text-[14px]">
                {userInfo.anniversaryDate}
              </p>
            </div>
            <div className="address  pt-6">
              <div className="sub-title">
                <p>기본 주소</p>
              </div>
              <p className="mr-1 w-full rounded-md bg-[#EFEFEF] p-3 px-2 font-cusFont3 text-[14px]">
                {userInfo.defaultAddr} {userInfo.detailAddr} {userInfo.zipCode}
              </p>
            </div>
            <div className="account  pt-6">
              <div className="sub-title">
                <p>기본 계좌</p>
              </div>

              <p className="mr-1 w-full rounded-md  bg-[#EFEFEF]  p-3 px-2 font-cusFont3 text-[14px] ">
                {userInfo.accountBank} {userInfo.accountNo}
              </p>
            </div>
          </div>
          <div
            id="buttonSection"
            className="absolute bottom-0 flex w-full flex-col items-center justify-around  pb-5"
          >
            <button
              onClick={handleEditClick}
              style={{ width: "calc(75% )" }} // 버튼 너비 조정
              className="common-btn"
            >
              수정 하기
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default MyPage;
