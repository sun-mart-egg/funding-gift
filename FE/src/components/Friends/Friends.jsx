import { useEffect, useState } from "react";
import refreshIcon from "/imgs/refreshIcon.png";
import searchIcon from "/imgs/searchIcon.png";
import searchIconTrue from "/imgs/searchIconTrue.png";
import filterIcon from "/imgs/filterIcon.png";
import filterIconTrue from "/imgs/filterIconTrue.png";
import fish from "/imgs/fish.PNG";
import star from "/imgs/star.png"
import graystar from "/imgs/graystar.png"
import axios from "axios";

function Friends() {
  const [isSearch, setIsSearch] = useState(false); // 검색창 on/off 위한 상태변수
  const [isFilter, setIsFilter] = useState(false); // 필터창 on/off 위한 상태변수
  const [friends, setFriends] = useState([]); // 친구목록 받아올 배열
  const [userInput, setUserInput] = useState(""); // 친구이름 검색
  const [filterOption, setsFilterOption] = useState("all") // 전체, 친한친구 목록 출력

  const searchState = () => {
    setIsSearch((prevSearch) => !prevSearch);
  };
  const filterState = () => {
    setIsFilter((prevFilter) => !prevFilter);
  };

  // 친구 이름 검색 입력에 대한 함수
  const handleInput = (event) => {
    setUserInput(event.target.value);
  };

  // 친구목록 필터링
  // 필터아이콘 클릭하면 전체, 친한친구가 나온다.
  // 기본값은 전체, 친한친구를 클릭 시 (filterOption === "favorites") 친구목록에서 favorite가 true인 친구들만 출력된다.
  const filteredFriends = () => {
    if (filterOption === "favorites") {
      return friends.filter((friend) => friend.favorite && friend.profileNickname.includes(userInput))
    }
    else {
      return friends.filter((friend) => friend.profileNickname.includes(userInput))
    }
  }

  // redis에 친구목록 요청하는 api
  useEffect(() => {
    axios
      .get(import.meta.env.VITE_BASE_URL + "/api/friends", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("access-token")}`,
        },
      })
      .then((res) => {
        console.log(res.data.data);
        setFriends(res.data.data);
        console.log("REDIS에 친구목록 요청 완료");
      })
      .catch((err) => {
        console.error(err);
        console.log("REDIS에 친구목록 요청 실패");
      });
  }, []);

  // 카카오 친구로 동기화를 위한 api 요청 함수
  const handleKAKAO = () => {
    axios.get(import.meta.env.VITE_BASE_URL + "/api/friends/kakao", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access-token")}`,
      },
    })
      .then((res) => {
        console.log(res.data.data.elements)
        setFriends(res.data.data.elements)
        console.log("KAKAO 친구목록 동기화 성공")
      })
      .catch((err) => {
        console.error(err)
        console.log("KAKAO 친구목록 동기화 실패")
      })
  }

  // 친한친구 설정을 위한 api 요청 함수
  const handleFavorite = (consumerId) => {
    axios.put(import.meta.env.VITE_BASE_URL + `/api/friends/${consumerId}/toggle-favorite`, {}, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access-token")}`,
      },
    })
      .then((res) => {
        console.log(res)
        console.log(friends)
        console.log("친한친구 설정 성공")
        setFriends((curFriends) =>
          curFriends.map((friend) => (
            friend.consumerId === consumerId ? { ...friend, favorite: !friend.favorite } : friend
          )))
      })
      .catch((err) => {
        console.error(err)
        console.log("친한친구 요청 실패")
      })
  }

  // 전체, 친한친구 필터링을 위한 핸들러 함수
  const handleFilterOption = (option) => {
    setsFilterOption(option)
  }

  return (
    <div className="justify-start sub-layer">
      <div className="absolute top-[40px] flex w-full flex-row justify-between">
        <div className="flex flex-row items-center p-3 ">
          <p className=" p-2.5 font-cusFont3 text-lg font-bold">
            친구 {friends.length}
          </p>
          <button onClick={handleKAKAO}>
            <img src={refreshIcon} alt="동기화아이콘" />
          </button>
        </div>

        <div className=" flex flex-row items-center p-2.5">
          {isSearch &&
            <input
              type="text"
              className=" m-1 h-[25px] w-[155px] rounded-[10px] border border-cusColor3 p-2"
              value={userInput}
              onChange={handleInput}
            />
          }
          <button onClick={searchState} className="p-1 ">
            <img
              src={isSearch ? searchIconTrue : searchIcon}
              alt="검색아이콘"
            />
          </button>
          <button onClick={filterState} className="p-1 ">
            <img
              src={isFilter ? filterIconTrue : filterIcon}
              alt="필터아이콘"
            />
          </button>
          {isFilter &&
            <div className="absolute flex flex-col top-[60px] right-[10px] bg-white z-10 rounded-lg w-[100px]">
              <button className={`${filterOption === "all" ? "bg-cusColor3" : "bg-white"} rounded-lg border`} onClick={() => handleFilterOption("all")}>전체</button>
              <button className={`${filterOption === "favorites" ? "bg-cusColor3" : "bg-white"} rounded-lg border`} onClick={() => handleFilterOption("favorites")}>친한친구</button>
            </div>}
        </div>
      </div>

      <div className="fixed top-[90px] flex h-full max-h-[calc(100vh-90px)] w-full max-w-[500px] flex-col items-center justify-start">
        <div className="w-full h-full gap-3 overflow-y-scroll">
          {filteredFriends().map((friend, index) => (
            <div
              key={index}
              className="flex flex-row items-center justify-between gap-3 m-2"
            >
              <div className="flex flex-row items-center gap-3"
                key={friend.consumerId}>
                <img
                  src={
                    friend.profileThumbnailImage === ""
                      ? fish
                      : friend.profileThumbnailImage
                  }
                  alt="카톡프사"
                  className="h-[100px] w-[100px] rounded-lg"
                />
                <h1 className="text-xl font-bold signup-font">
                  {friend.profileNickname}
                </h1>
              </div>
              <button onClick={() => handleFavorite(friend.consumerId)}>
                <img src={friend.favorite ? star : graystar} alt="친한친구 유무" />
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Friends;
