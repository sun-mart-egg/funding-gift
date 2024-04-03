import React, { useState, useEffect } from "react";
import egg from "/imgs/egg3.jpg";
import { IoLogOut } from "react-icons/io5";
import { AiFillCamera } from "react-icons/ai";
import axios from "axios";
import { useNavigate } from "react-router";

function MyPage() {
  const navigate = useNavigate();
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
    img: { egg },
    // 추가 정보가 있다면 여기에 포함할 수 있습니다.
  });

  //사용자 정보 받아오기
  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get(
          import.meta.env.VITE_BASE_URL + "/api/consumers",
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("access-token")}`,
            },
          },
        );

        // 응답 데이터 콘솔에 출력
        console.log("Received user info:", response.data.data.profileImageUrl);

        // API 응답으로부터 받은 데이터로 userInfo 상태 업데이트
        setUserInfo({
          ...userInfo,
          name: response.data.data.name,
          // anniversaryDate: response.data.anniversaryDate,
          // defaultAddr: response.data.defaultAddr,
          // detailAddr: response.data.detailAddr,
          // zipCode: response.data.zipCode,
          // accountBank: response.data.accountBank,
          // accountNo: response.data.accountNo,
          img: response.data.data.profileImageUrl,
        });
      } catch (error) {
        console.error("Failed to fetch user info:", error);
      }
    };

    fetchUserInfo();
  }, []);
  // 수정 버튼 클릭 시 호출될 함수
  const handleEditClick = () => {
    setIsEditMode(!isEditMode);
  };

  // 사용자 이름 변경 시 호출될 함수
  const handleNameChange = (event) => {
    setUserInfo({ ...userInfo, name: event.target.value });
  };

  // 로그아웃 로직
  const logOut = () => {
    // axios 요청
    axios
      .post(import.meta.env.VITE_BASE_URL + "/api/consumers/logout", null, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("access-token")}`,
        },
      })

      // 요청 성공시
      .then((res) => {
        console.log(res);
        window.alert("로그아웃 했습니다.");
        // console.log("로그아웃 했습니다.")
        // 로컬 스토리지 초기화
        localStorage.clear();
        navigate("/");
      })
      .catch((err) => {
        console.error(err);
        console.log("로그아웃 실패!");
      });
  };

  // fcm 토큰 지우기
  const deleteFCM = () => {
    axios.delete(import.meta.env.VITE_BASE_URL + "/api/fcm-tokens", {
      data: {
        "fcmToken": localStorage.getItem("fcm-token")
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access-token")}`,
      }
    })
    .then((res) => {
      console.log(res)
      console.log("fcm 토큰 삭제 성공")
    })
    .catch((err) => {
      console.error(err)
      console.log("fcm 토큰 삭제 실패")
    })
  }


  // 진행중인 펀딩이 있는지 확인하고
  // 펀딩이 있는 경우 회원탈퇴 못해요 ^^
  const checkMyFunding = () => {
    axios.get(import.meta.env.VITE_BASE_URL + "/api/consumers/in-progress-funding", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access-token")}`,
      },
    })
    .then((res) => {
      if(res.data.data) {
        alert("너 진행중인 펀딩 있어")
      }
      else {
        signOut()
      }
    })
    .catch((err) => {
      console.error(err)
      console.log("진행중인 펀딩 확인 에러")
    })
  }
  
  // 회원탈퇴 관련 ( 카카오과의 연결을 끊음 )
  const BYE_BYE_URL =
    "https://j10d201.p.ssafy.io/oauth2/authorization/kakao?redirect_uri=https://j10d201.p.ssafy.io&mode=unlink";

  const signOut = () => {
    localStorage.clear();
    window.location.replace(BYE_BYE_URL);
    window.alert("회원탈퇴 완료");
    console.log("카카오측과의 연결을 끊었습니다.");
  };

  return (
    <div className="sub-layer font-cusFont3">
      {isEditMode ? (
        // 수정 모드 활성화 시 보여줄 UI
        <>
          <div className="absolute flex items-center w-full px-6 head top-20">
            <div className="relative mr-4 ">
              <img
                src={userInfo.img}
                alt=""
                className=" h-[80px] w-[80px] rounded-full"
              />
              {/* 절대 위치를 사용한 카메라 아이콘 */}
              <AiFillCamera
                className="absolute bottom-0 right-1 text-[20px] text-[#9B9B9B]"
                style={{ bottom: "-10px", right: "1px" }}
              />
            </div>
            <div className="flex w-[70%] justify-between">
              <input
                type="text"
                value={userInfo.name}
                onChange={handleNameChange}
                className="mr-1 w-full rounded-md border border-gray-400 px-2 font-cusFont5 text-[25px]"
              />
            </div>
          </div>
          <div className="absolute w-full px-6 content top-52 font-cusFont3">
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
              <div className="pt-6 sub-title ">
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
              <div className="pt-6 sub-title">
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
          <button
            className="absolute bottom-16 right-[15%] pb-3 text-[12px] text-gray-300"
            onClick={checkMyFunding}
          >
            회원 탈퇴
          </button>
          <div
            id="buttonSection"
            className="absolute bottom-0 flex flex-col items-center justify-around w-full pb-5"
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
          <div className="absolute flex items-center w-full px-6 head top-20">
            <img
              src={userInfo.img}
              alt=""
              className=" mr-4 h-[80px] w-[80px] rounded-full"
            />
            <div className="flex w-[70%] justify-between ">
              <p className="mr-1 px-2 font-cusFont5 text-[25px]">
                {userInfo.name}
              </p>
              <button
                className="flex flex-col items-center justify-center"
                onClick={logOut}
              >
                <IoLogOut className="text-[25px]" />
                <p className="text-[10px]">로그아웃</p>
              </button>
            </div>
          </div>

          {/* Content 입니다. */}
          <div className="absolute w-full px-6 content top-52">
            <div className="birthday">
              <div className="sub-title">
                <p>생일</p>
              </div>
              <p className="mr-1 w-full rounded-md bg-[#EFEFEF] p-3 px-2 font-cusFont3 text-[14px]">
                {userInfo.anniversaryDate}
              </p>
            </div>
            <div className="pt-6 address">
              <div className="sub-title">
                <p>기본 주소</p>
              </div>
              <p className="mr-1 w-full rounded-md bg-[#EFEFEF] p-3 px-2 font-cusFont3 text-[14px]">
                {userInfo.defaultAddr} {userInfo.detailAddr} {userInfo.zipCode}
              </p>
            </div>
            <div className="pt-6 account">
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
            className="absolute bottom-0 flex flex-col items-center justify-around w-full pb-5"
          >
            <button
              onClick={handleEditClick}
              style={{ width: "calc(75% )" }} // 버튼 너비 조정
              className="common-btn"
            >
              수정 하기
            </button>
          </div>
          <button onClick={deleteFCM} className="absolute border border-black bottom-20">FCM-TOKEN-DELETE</button>
        </>
      )}
    </div>
  );
}

export default MyPage;
