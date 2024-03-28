import { BsPeopleFill } from "react-icons/bs";
import { IoMdSettings } from "react-icons/io";
import SearchBar from "../../UI/SearchBar";
import CardList from "../component/CardList";
import { useState, useEffect } from "react";
import { AiOutlinePlus } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function MyFunding() {
  const navigate = useNavigate();
  const [buttonSelected, setButtonSelected] = useState(true);
  const [myFundings, setMyFundings] = useState([]); // API로부터 받은 데이터를 저장할 상태

  const fetchMyFundings = async () => {
    try {
      const response = await axios.get(
        import.meta.env.VITE_BASE_URL + "/api/fundings/my-fundings",
        {
          headers: {
            Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NyIsImlhdCI6MTcxMTYxMzgyMiwiZXhwIjoxNzExNzkzODIyfQ.Uqx-YSlRiI6Buiqdeh43Qel7F-hEbuKRxnmD-qQolBKbl8lX1CwGJ52NoAN3MPiKv71BJUzfLlaUtEcaaGw_WQ`,
          },
          params: {
            page: 0,
            size: 8,
          },
          paramsSerializer: (params) => {
            // 직접 쿼리 스트링을 구성
            return `page=${params.page}&size=${params.size}&sort=createdAt&sort=DESC`;
          },
        },
      );
      setMyFundings(response.data.data.data);
      console.log(response.data.data.data);
    } catch (error) {
      console.error("내가 만든 펀딩을 불러오는데 실패했습니다.", error);
    }
  };

  useEffect(() => {
    fetchMyFundings();
  }, []);

  const handleClickButton = (e) => {
    const buttonName = e.target.name;
    setButtonSelected(buttonName === "myFunding");

    if (buttonName === "myFunding") {
      fetchMyFundings();
    }
  };

  const handleCreateFundingClick = () => {
    navigate("/make-funding-main");
  };

  return (
    <div className="main-layer ">
      <div id="profileSection" className="m-2 flex w-full justify-between p-2">
        <div id="leftSection" className="flex items-center space-x-4">
          <img
            src="src\components\Funding\assets\egg3.jpg"
            alt="프로필"
            className="h-16 w-16 rounded-full border-2 border-gray-300"
          />
          <p className="font-cusFont5 text-xl">김싸피</p>
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
          <CardList data={myFundings} />
        ) : (
          <CardList data={data2} />
        )}
      </div>
      <button
        onClick={handleCreateFundingClick}
        className="hover:bg-cusColor3-dark fixed bottom-20 right-4 flex h-16 w-16 flex-col items-center justify-center rounded-full bg-cusColor3 text-white shadow-lg"
      >
        <AiOutlinePlus size="20" />
        <p className="text-xs">펀딩만들기</p>
      </button>
    </div>
  );
}

let data = [
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
    progress: 100,
  },
  {
    id: 3,
    title: "FOOD IS MY LIFE",
    name: "고오급 케이크",
    date: "2024.4.15 ~ 2024.4.22",
    progress: 20,
  },
];

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
