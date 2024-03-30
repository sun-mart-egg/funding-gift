import { BsPeopleFill } from "react-icons/bs";
import { IoMdSettings } from "react-icons/io";
import SearchBar from "../../UI/SearchBar";
import CardList from "../component/CardList";
import { useState, useEffect } from "react";
import { AiOutlinePlus } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { fetchMyFundings } from "../api/FundingAPI";

function MyFunding() {
  const navigate = useNavigate();
  const [buttonSelected, setButtonSelected] = useState(true);
  const [myFundings, setMyFundings] = useState([]); // API로부터 받은 데이터를 저장할 상태
  const [isLoading, setIsLoading] = useState(true);

  const [userInfo, setUserInfo] = useState({
    name: "신시은",
    img: null,
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
          img: response.data.data.profileImageUrl,
        });
      } catch (error) {
        console.error("Failed to fetch user info:", error);
      }
    };

    fetchUserInfo();
  }, []);

  //내 펀딩 조회 api
  useEffect(() => {
    const token = localStorage.getItem("access-token");
    if (!token) {
      console.log("토큰이 존재하지 않습니다.");
      setIsLoading(false);
      navigate("/login-page");
      return;
    }
    fetchMyFundings(token, setMyFundings, setIsLoading);
  }, []);

  //버튼 클릭 시 api 불러오는거 연결
  const handleClickButton = (e) => {
    const token = localStorage.getItem("access-token");
    const buttonName = e.target.name;
    setButtonSelected(buttonName === "myFunding");
    if (!token) {
      console.log("토큰이 존재하지 않습니다.");
      setIsLoading(false);
      navigate("/login-page");
      return;
    }
    if (buttonName === "myFunding") {
      fetchMyFundings(token, setMyFundings, setIsLoading);
    }
  };

  const handleCreateFundingClick = () => {
    navigate("/make-funding-main");
  };

  return (
    <div className="main-layer ">
      <div
        id="profileSection"
        className=" flex w-full justify-between p-2 px-6"
      >
        <div id="leftSection" className="flex items-center space-x-4">
          <img
            src={userInfo.img}
            alt="프로필"
            className="h-[60px] w-[60px] rounded-full  border-gray-300"
          />
          <p className="font-cusFont5 text-xl">{userInfo.name}</p>
        </div>

        <div id="rightSection" className="flex items-center space-x-0">
          <button
            id="friendButton"
            className="flex flex-col items-center space-y-1 p-2"
            onClick={() => navigate("/friends")}
          >
            <BsPeopleFill size="20" />
            <p className="text-sm">친구</p>
          </button>

          <button
            onClick={() => navigate("/my-page")}
            className="flex flex-col items-center space-y-1 p-2"
          >
            <IoMdSettings size="20" />
            <p className="text-sm">설정</p>
          </button>
        </div>
      </div>
      <div id="buttonSection" className="flex w-full justify-between  ">
        <button
          onClick={handleClickButton}
          name="myFunding"
          className={
            buttonSelected
              ? ` w-3/6 bg-cusColor3 p-4 text-xs text-white `
              : `w-3/6 border-b border-t border-cusColor3 p-4 text-xs`
          }
        >
          내가 만든 펀딩 ({myFundings.length})
        </button>
        <button
          onClick={handleClickButton}
          name="friendsFunding"
          className={
            !buttonSelected
              ? `w-3/6 bg-cusColor3 p-4 text-xs text-white `
              : `w-3/6 border-b border-t border-cusColor3 p-4 text-xs`
          }
        >
          내가 참여한 펀딩 ({data2.length})
        </button>
      </div>

      <div id="mainSection" className=" flex-center w-full flex-col p-4">
        <SearchBar />

        {buttonSelected ? (
          myFundings.length === 0 ? (
            <div className="m-1 flex flex-col items-center justify-start p-10 font-cusFont3 text-[20px]">
              아직 만들어진 펀딩이 없습니다.{" "}
            </div>
          ) : (
            <CardList data={myFundings} />
          )
        ) : (
          <CardList data={data2} />
        )}
      </div>
      <button
        onClick={handleCreateFundingClick}
        className="hover:bg-cusColor3-dark fixed bottom-24 right-4 flex h-12 w-12 flex-col items-center justify-center rounded-full bg-cusColor3 text-white shadow-lg"
      >
        <AiOutlinePlus size="20" />
      </button>
    </div>
  );
}

let data2 = [
  {
    id: 0,
    title: "GAME IS MY LIFE",
    name: "닌텐도 스위치",
    date: "2024.4.15 ~ 2024.4.22",
    progress: 10,
  },

  {
    id: 1,
    title: "HEALTH IS MY LIFE",
    name: "단백질 세트",
    date: "2024.4.15 ~ 2024.4.22",
    progress: 70,
  },

  {
    id: 2,
    title: "MUSIC IS MY LIFE",
    name: "에어팟 맥스",
    date: "2024.4.15 ~ 2024.4.22",
    progress: 40,
  },
];

export default MyFunding;
