import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import egg from "/imgs/egg3.jpg";
import ProgressBar from "../component/ProgressBar";
import { IoClose } from "react-icons/io5";

function StoryPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const [progress, setProgress] = useState(0);
  const [stories, setStories] = useState([]);

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const storiesData = queryParams.get("data");
    if (storiesData) {
      setStories(JSON.parse(decodeURIComponent(storiesData)));
    }

    const timer = setTimeout(() => {
      navigate(-1); // 30초 후에 이전 페이지로 돌아갑니다.
    }, 30000);

    const interval = setInterval(() => {
      setProgress((oldProgress) => {
        if (oldProgress >= 100) {
          clearInterval(interval);
          return 100;
        }
        return Math.min(oldProgress + 100 / (30000 / 100), 100);
      });
    }, 100);

    return () => {
      clearTimeout(timer);
      clearInterval(interval);
    };
  }, [navigate, location.search]);

  return (
    <div
      className="story-layer "
      style={{
        background: "linear-gradient(to bottom, #E5EEFF, #FFFFFF)",
      }}
    >
      <div className="fixed top-0 w-full bg-[#ddd]">
        <div
          id="progress"
          style={{
            height: "10px",
            width: `${progress}%`,
            backgroundColor: "blue",
            transition: "width 100ms linear",
          }}
        />
      </div>
      <button onClick={() => navigate("/funding")}>
        {" "}
        <IoClose className="fixed right-5 top-8 text-[30px]" />
      </button>

      {stories.map((story, index) => (
        <div
          key={index}
          className="  absolute top-20 flex h-[68%] w-[75%] flex-col items-center justify-evenly rounded-xl bg-white p-4 shadow-md"
        >
          <div className="flex flex-col items-center justify-center text-center font-cusFont3">
            <div
              id="imgSection"
              className="mb-6 flex w-2/3 items-center justify-center text-center"
            >
              {/* 이미지의 alt 속성에 story.title을 사용하여 접근성 향상 */}
              <img
                src={story.img || egg}
                alt={story.title || "Story image"}
                className="mx-auto"
              />
            </div>
            <div id="itemInfo">
              {/* 중괄호 닫힘 오류 수정 */}
              <p className="py-2 font-cusFont3 text-sm">{story.people} 님의 </p>
              <p className="pb-2 font-cusFont1 text-2xl">{story.title}</p>
              <p className="pb-2 font-cusFont2 text-sm">{story.name}</p>
              <p className="pb-2 font-cusFont2 text-sm">760,000</p>
              <p className="pb-4 font-cusFont2 text-sm">{story.date}</p>
              <div className="h-[18px]">
                <ProgressBar progress={story.progress} />
              </div>
            </div>
          </div>
        </div>
      ))}

      <button
        style={{ width: "calc(75% )" }} // 버튼 너비 조정
        className="common-btn absolute bottom-10  w-full"
      >
        자세히 보기
      </button>
    </div>
  );
}

export default StoryPage;
