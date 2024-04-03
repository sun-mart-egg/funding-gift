import React, { useEffect, useState, useRef, useCallback } from "react";
import StoryList from "../component/StoryList";
import FundingList from "../component/FundingList";
import ScrollToTopButton from "../../UI/ScrollToTop";
import { getStoryList } from "../api/StoryAPI";
import { getFundingFeed } from "../api/FundingAPI";

function FundingMain() {
  const [storyList, setStoryList] = useState([]);
  const [feedData, setFeedData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  const observer = useRef();

  const lastFeedElementRef = useCallback(
    (node) => {
      if (loading) return;
      if (observer.current) observer.current.disconnect();
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasMore) {
          setCurrentPage((prevPage) => prevPage + 1);
        }
      });
      if (node) observer.current.observe(node);
    },
    [loading, hasMore]
  );

  useEffect(() => {
    loadFeedData();
  }, [currentPage]); // eslint-disable-line react-hooks/exhaustive-deps

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

  const loadFeedData = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("access-token");
      const response = await getFundingFeed(token, currentPage);
      const newData = response.data.data;
      setFeedData((prevData) => [...prevData, ...newData]);
      setHasMore(response.data.hasNext === true);
    } catch (error) {
      console.error("Failed to load funding feed:", error);
    }
    setLoading(false);
  };

  return (
    <div className="sub-layer relative">
      <div className="story absolute inset-x-0 top-14 flex justify-start border-b border-gray-400 font-cusFont3 text-xs">
        <div className="MyStory flex-none flex-col items-center justify-center p-4">
          {/* Your story component */}
        </div>
        <div className="friendStory flex overflow-x-auto">
          <StoryList listData={storyList} />
        </div>
      </div>

      <div className="main absolute top-44 w-full pb-24">
        <FundingList listData={feedData} friendsData={storyList} />
        {loading && <div>Loading...</div>}
        <div ref={lastFeedElementRef} />
      </div>
      <ScrollToTopButton className="bottom-[25px]" />
    </div>
  );
}

export default FundingMain;
