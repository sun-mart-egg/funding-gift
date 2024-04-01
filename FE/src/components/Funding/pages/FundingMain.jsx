import React, { useEffect, useState } from "react";
import StoryList from "../component/StoryList";
import FundingList from "../component/FundingList";
import ScrollToTopButton from "../../UI/ScrollToTop";
import { getStoryList } from "../api/StoryAPI";

function FundingMain() {
  const [storyList, setStoryList] = useState([]); // 친구목록 받아올 배열
  const [data, setData] = useState([]);
  //친구가 만든 펀딩 받아올 배열
  const [isLoading, setIsLoading] = useState(true);
  let myData = {
    people: "신시은",
    img: "/imgs/egg3.jpg",
  };

  useEffect(() => {
    console.log("업데이트 된 스토리 목록");
  }, [storyList]);

  //스토리 리스트 불러오는 api
  useEffect(() => {
    const token = localStorage.getItem("access-token"); // 토큰을 localStorage에서 가져옵니다.
    const fetchStoryList = async () => {
      try {
        const storyListData = await getStoryList(token);
        setStoryList(Array.isArray(storyListData) ? storyListData : []);
      } catch (error) {
        console.error("친구 목록을 불러오는데 실패했습니다.", error);
        setStoryList([]);
      }
    };

    fetchStoryList();
  }, []);

  return (
    <div className="sub-layer relative">
      <div className="story absolute inset-x-0 top-14 flex justify-start border-b border-gray-400 font-cusFont3 text-xs">
        <div className="MyStory flex-none flex-col items-center justify-center p-4">
          <img
            src={myData.img}
            alt={myData.people}
            className="h-14 w-14 rounded-full" // 너비와 높이를 24로 설정
          />
          <p className="text-center">{myData.people}</p>
        </div>
        <div className="friendStory  flex overflow-x-auto">
          <StoryList listData={storyList} />
        </div>
      </div>

      <div className="main absolute top-44 w-full  pb-24">
        <FundingList listData={data} friendsData={storyList} />
      </div>
      <ScrollToTopButton className="bottom-[25px]" />
    </div>
  );
}

export default FundingMain;
