import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function StoryPage({ item }) {
  const navigate = useNavigate();
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate(-1); // 1분 후에 이전 페이지로 돌아갑니다.
    }, 60000); // 60000ms = 1분

    const interval = setInterval(() => {
      setProgress((oldProgress) => {
        if (oldProgress === 100) {
          clearInterval(interval);
          return 100;
        }
        return Math.min(oldProgress + 100 / 60, 100); // 1분 동안 매 초마다 증가
      });
    }, 1000); // 1초마다 업데이트

    return () => {
      clearTimeout(timer);
      clearInterval(interval);
    };
  }, [navigate]);

  return (
    <div className="story-page">
      <div style={{ width: "100%", backgroundColor: "#ddd" }}>
        <div
          style={{
            height: "5px",
            width: `${progress}%`,
            backgroundColor: "blue",
          }}
        />
      </div>
      안녕
    </div>
  );
}

export default StoryPage;
