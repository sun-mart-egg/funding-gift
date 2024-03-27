import { useState } from "react";
import refreshIcon from "/imgs/refreshIcon.png";
import searchIcon from "/imgs/searchIcon.png";
import searchIconTrue from "/imgs/searchIconTrue.png";
import filterIcon from "/imgs/filterIcon.png";
import filterIconTrue from "/imgs/filterIconTrue.png";

function Friends() {
  const [isSearch, setIsSearch] = useState(false);
  const [isFilter, setIsFilter] = useState(false);
  const searchState = () => {
    setIsSearch((prevSearch) => !prevSearch);
  };
  const filterState = () => {
    setIsFilter((prevFilter) => !prevFilter);
  };
  const myFriends = [
    {
      number: 1,
      name: "종혁1",
    },
    {
      number: 2,
      name: "종혁2",
    },
    {
      number: 3,
      name: "종혁3",
    },
    {
      number: 4,
      name: "종혁4",
    },
    {
      number: 5,
      name: "종혁5",
    },
    {
      number: 6,
      name: "종혁6",
    },
    {
      number: 7,
      name: "종혁7",
    },
  ];
  const [isChin, setIsChin] = useState(false);
  const handleChin = () => {
    setIsChin((prevChin) => !prevChin);
  };

  return (
    <div className="sub-layer">
      <div className="flex flex-row justify-between w-full">
        <div className="flex flex-row items-center p-3 ">
          <p className=" p-2.5 font-cusFont3 text-lg font-bold">친구 8</p>
          <button>
            <img src={refreshIcon} alt="동기화아이콘" />
          </button>
        </div>

        <div className=" flex flex-row items-center p-2.5">
          {isSearch ? (
            <input
              type="text"
              className=" m-1 h-[25px] w-[160px] rounded-[10px] border border-cusColor3"
            />
          ) : (
            ""
          )}
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
        </div>
      </div>

      <p className="text-4xl  font-cusFont5">친구목록 나올거에요</p>

      <div className="grid w-full max-w-[450px] grid-flow-row grid-cols-1 gap-3">
        {myFriends.map((friend) => (
          <div
            key={friend.name}
            className="flex flex-row items-center justify-start gap-4"
          >
            <div className="signup-font flex h-[60px] w-[60px] items-center justify-center rounded bg-cyan-300">
              사진
            </div>
            <p className="signup-font">{friend.name}</p>
            <button
              className="h-full bg-yellow-200 border signup-font"
              onClick={handleChin}
            >
              {isChin ? "친한친구 맞음" : "친한친구 아님"}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Friends;
