import { useEffect, useState } from "react";
import refreshIcon from "/imgs/refreshIcon.png";
import searchIcon from "/imgs/searchIcon.png";
import searchIconTrue from "/imgs/searchIconTrue.png";
import filterIcon from "/imgs/filterIcon.png";
import filterIconTrue from "/imgs/filterIconTrue.png";
import fish from "/imgs/fish.PNG"
import axios from "axios";

function Friends() {
  const [isSearch, setIsSearch] = useState(false);
  const [isFilter, setIsFilter] = useState(false);
  const [friends, setFriends] = useState([])

  const searchState = () => {
    setIsSearch((prevSearch) => !prevSearch);
  };
  const filterState = () => {
    setIsFilter((prevFilter) => !prevFilter);
  };

  // 카카오톡 친구목록 불러오는 api
  useEffect(() => {
    axios.get(import.meta.env.VITE_BASE_URL + "/api/friends/kakao", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access-token")}`
      }
    })
      .then((res) => {
        console.log(res.data.data.elements)
        setFriends(res.data.data.elements)
        console.log("친구목록 받아왔다.")
      })
      .catch((err) => {
        console.error(err)
        console.log("안 된다 ㅠㅠ")
      })
  }, [])

  return (
    <div className="sub-layer top-[40px] justify-start">
      <div className="flex flex-row justify-between w-full">
        <div className="flex flex-row items-center p-3 ">
          <p className=" p-2.5 font-cusFont3 text-lg font-bold">친구 {friends.length}</p>
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

      <div className='max-w-[500px] w-full h-full flex flex-col items-center justify-start'>
        <div className="w-full h-full gap-3 overflow-y-scroll">
          {friends.map((friend, index) => (
            <div key={index} className='flex flex-row items-center justify-between gap-3 m-2'>
              <div className='flex flex-row items-center gap-3'>
                <img src={friend.profileThumbnailImage === "" ? fish : friend.profileThumbnailImage} alt="카톡프사" className='w-[100px] h-[100px] rounded-lg' />
                <h1 className='text-xl font-bold signup-font'>{friend.profileNickname}</h1>
              </div>
              {friend.profileThumbnailImage === "" ? "기본프사" : "👍"}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Friends;
