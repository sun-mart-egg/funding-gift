import { BsPeopleFill } from "react-icons/bs";
import { IoMdSettings } from "react-icons/io";
import SearchBar from "../../UI/SearchBar";
import CardList from "../component/CardList";
import { useState, useEffect } from "react";
import { AiOutlinePlus } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import { fetchFriendFunding } from "../api/FundingAPI";
import { getFriendInfo } from "../api/UserAPI";
import { useParams } from "react-router-dom";

function FriendFunding() {
  const { consumerId } = useParams(); // URL 파라미터에서 consumer-id 값을 추출합니다.
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);

  const [friendFunding, setFriendFunding] = useState([]);

  const [FriendInfo, setFriendInfo] = useState({
    name: "",
    img: null,
    // 추가 정보가 있다면 여기에 포함할 수 있습니다.
  });
  const [fetchData, setFetchData] = useState(false); // 데이터를 불러올지 결정하는 상태

  //사용자 정보 받아오기
  useEffect(() => {
    const token = localStorage.getItem("access-token");
    if (consumerId && token) {
      setFetchData(true); // consumerId와 token이 준비되면 데이터를 불러올 준비를 함
    } else if (!token) {
      navigate("/login-page");
    }
  }, [consumerId, navigate]);

  useEffect(() => {
    const token = localStorage.getItem("access-token");

    if (fetchData) {
      const fetchFriends = async () => {
        try {
          const friendsData = await getFriendInfo(token, consumerId);
          setFriendInfo({
            name: friendsData.data.name,
            img: friendsData.data.profileImageUrl,
          });
        } catch (error) {
          console.error("친구 정보 불러오기 실패:", error);
        } finally {
          setIsLoading(false);
        }
      };

      fetchFriends();
      fetchFriendFunding(consumerId, token, setFriendFunding);
      setFetchData(false);
    }
  }, [fetchData, consumerId]);

  useEffect(() => {
    console.log("가져온 펀딩 입니다. " + friendFunding);
  }, [friendFunding]);

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
      </div>
      <div id="buttonSection" className="flex w-full justify-between  ">
        <button
          name="myFunding"
          className={`w-full bg-cusColor3 p-4 text-xs text-white `}
        >
          친구가 만든 펀딩 ({friendFunding.length})
        </button>
      </div>

      <div id="mainSection" className=" flex-center w-full flex-col p-4">
        <SearchBar />

        {friendFunding.length === 0 ? (
          <div className="m-1 flex flex-col items-center justify-start p-10 font-cusFont3 text-[20px]">
            아직 만들어진 펀딩이 없습니다.{" "}
          </div>
        ) : (
          <CardList data={friendFunding} basePath="/friend-funding-detail" />
        )}
      </div>
    </div>
  );
}

export default FriendFunding;
