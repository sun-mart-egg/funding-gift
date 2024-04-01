import { BsPeopleFill } from "react-icons/bs";
import { IoMdSettings } from "react-icons/io";
import SearchBar from "../../UI/SearchBar";
import CardList from "../component/CardList";
import { useState, useEffect } from "react";
import { AiOutlinePlus } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { fetchMyFundings } from "../api/FundingAPI";
import { getFriendInfo } from "../api/UserAPI";
import { useParams } from "react-router-dom";

function FriendFunding() {
  const { consumerId } = useParams(); // URL 파라미터에서 consumer-id 값을 추출합니다.
  const navigate = useNavigate();
  const [myFundings, setMyFundings] = useState([]); // API로부터 받은 데이터를 저장할 상태
  const [isLoading, setIsLoading] = useState(true);
  const [FriendInfo, setFriendInfo] = useState({
    name: "",
    img: null,
    // 추가 정보가 있다면 여기에 포함할 수 있습니다.
  });

  //사용자 정보 받아오기
  useEffect(() => {
    const fetchFriends = async () => {
      try {
        const token = localStorage.getItem("access-token");
        if (!token) {
          console.log("토큰이 존재하지 않습니다.");
          setIsLoading(false);
          navigate("/login-page");
          return;
        }
        const friendsData = await getFriendInfo(token, consumerId);
        setFriendInfo({
          name: friendsData.data.name,
          img: friendsData.data.profileImageUrl,
        });
        setIsLoading(false);
      } catch (error) {
        console.error("친구 정보 불러오기 실패:", error);
        setIsLoading(false);
      }
    };

    if (consumerId) {
      fetchFriends();
    }
  }, [consumerId, navigate]);

  //친구가 만든 펀딩 정보 불러오기

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
            src={FriendInfo.img}
            alt="프로필"
            className="h-[60px] w-[60px] rounded-full  border-gray-300"
          />
          <p className="font-cusFont5 text-xl">{FriendInfo.name}</p>
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
          name="myFunding"
          className={`w-full bg-cusColor3 p-4 text-xs text-white `}
        >
          친구가 만든 펀딩 ({myFundings.length})
        </button>
      </div>

      <div id="mainSection" className=" flex-center w-full flex-col p-4">
        <SearchBar />

        {myFundings.length === 0 ? (
          <div className="m-1 flex flex-col items-center justify-start p-10 font-cusFont3 text-[20px]">
            아직 만들어진 펀딩이 없습니다.{" "}
          </div>
        ) : (
          <CardList data={myFundings} />
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

export default FriendFunding;
