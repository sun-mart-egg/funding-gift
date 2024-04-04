import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import egg from "/imgs/egg3.jpg";
import ProgressBar from "../component/ProgressBar";
import { getStory } from "../api/StoryAPI"; // API 호출 함수를 임포트합니다.
import { IoClose } from "react-icons/io5";
import { HiMiniBackward } from "react-icons/hi2";
import { HiMiniForward } from "react-icons/hi2";
function StoryPage() {
  const { selectedItem } = useParams(); // URL에서 selectedItem 값을 추출합니다.
  const [stories, setStories] = useState([]);
  const [currentStoryIndex, setCurrentStoryIndex] = useState(0);
  const [progress, setProgress] = useState(0); // 프로그레스 상태 초기화
  const navigate = useNavigate();

  const goToPreviousStory = () => {
    if (currentStoryIndex > 0) {
      setCurrentStoryIndex(currentStoryIndex - 1);
      setProgress(0);
    } else {
      alert("첫 스토리 입니다.");
    }
  };

  const goToNextStory = () => {
    if (currentStoryIndex < stories.length - 1) {
      setCurrentStoryIndex(currentStoryIndex + 1);
      setProgress(0);
    } else {
      alert("마지막 스토리 입니다.");
    }
  };

  useEffect(() => {
    console.log("현재 stories 상태:", stories);
  }, [stories]);

  useEffect(() => {
    const token = localStorage.getItem("access-token");
    getStory(token, selectedItem).then((data) => {
      if (data && data.length > 0) {
        setStories(data);
      } else {
        alert("이 사용자의 스토리가 없습니다.");
        navigate("/funding");
      }
    });
  }, [selectedItem, navigate]);

  useEffect(() => {
    if (stories.length > 0) {
      const progressInterval = setInterval(() => {
        setProgress((prevProgress) => {
          const nextProgress = prevProgress + 0.333;
          if (nextProgress >= 100) {
            clearInterval(progressInterval); // 프로그레스가 100%에 도달하면 인터벌을 중지합니다.
            changeStory(); // 다음 스토리로 넘어가는 함수를 호출합니다.
          }
          return nextProgress;
        });
      }, 100);

      return () => {
        clearInterval(progressInterval);
      };
    }
  }, [currentStoryIndex, stories.length]);

  const changeStory = () => {
    if (currentStoryIndex >= stories.length - 1) {
      alert("모든 스토리를 확인하셨습니다.");
      navigate("/funding");
    } else {
      setCurrentStoryIndex(currentStoryIndex + 1);
      setProgress(0);
    }
  };

  if (stories.length === 0) {
    return <div>Loading...</div>;
  }

  const currentStory = stories[currentStoryIndex];

  return (
    <div
      className="story-layer fixed top-0 w-full "
      style={{
        background: "linear-gradient(to bottom, #E5EEFF, #FFFFFF)",
        padding: 0,
      }}
    >
      <div
        className="w-full bg-[#ddd]"
        style={{ position: "absolute", top: 0, left: 0 }}
      >
        <div
          id="progress"
          className="bg-cusColor1"
          style={{
            height: "10px",
            width: `${progress}%`,
            transition: "width 100ms linear",
          }}
        />
      </div>
      <button onClick={() => navigate("/funding")}>
        <IoClose className="fixed right-5 top-8 text-[30px]" />
      </button>

      <HiMiniBackward
        className="fixed left-2 top-1/2 z-10 cursor-pointer text-[20px] text-cusColor1"
        onClick={goToPreviousStory}
      />
      <HiMiniForward
        className="fixed right-2 top-1/2 z-10 cursor-pointer text-[20px] text-cusColor1"
        onClick={goToNextStory}
      />

      <div className="absolute top-20 flex h-[68%] w-[75%] flex-col items-center justify-center rounded-xl bg-white p-4 shadow-md">
        <img
          src={currentStory.productImage || egg}
          alt={currentStory.title || "Story image"}
          className="mb-6  max-w-[50%] self-center" // 너비 조정 및 중앙 정렬
        />
        <div id="itemInfo" className="w-full text-center">
          <p className="py-2 font-cusFont3 text-sm">
            {currentStory.consumerName} 님의{" "}
          </p>
          <p className="pb-2 font-cusFont1 text-2xl">{currentStory.title}</p>
          <p className="pb-2 font-cusFont2 text-sm">
            {currentStory.productName}
          </p>
          <p className="pb-2 font-cusFont2 text-sm">
            {currentStory.targetPrice} 원
          </p>
          <p className="pb-4 font-cusFont2 text-sm">
            {currentStory.anniversaryDate}
          </p>
          <div className="flex w-full items-center justify-center">
            <div id="productProgress" className="h-[18px] w-[80%]">
              <ProgressBar
                progress={
                  (currentStory.sumPrice / currentStory.targetPrice) * 100
                }
              />
            </div>
          </div>
        </div>
      </div>

      <button
        onClick={() =>
          navigate(`/friend-funding-detail/${currentStory.fundingId}`)
        }
        style={{ width: "calc(75% )" }} // 버튼 너비 조정
        className="common-btn absolute bottom-10  w-full"
      >
        자세히 보기
      </button>
    </div>
  );
}

export default StoryPage;
