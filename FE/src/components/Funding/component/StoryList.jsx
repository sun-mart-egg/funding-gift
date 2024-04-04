import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function StoryList({ listData }) {
  const navigate = useNavigate();

  const handleStoryClick = (consumerId) => {
    navigate(`/story/${consumerId}`);
  };

  return (
    <div
      id="storyList"
      className="flex w-full overflow-x-scroll font-cusFont3 text-[12px]"
    >
      {Array.isArray(listData) &&
        listData.map((item, index) => (
          <div
            id="storyItem"
            key={index}
            className="flex-none cursor-pointer flex-col items-center justify-center p-4"
            onClick={() => handleStoryClick(item.consumerId)}
          >
            <img
              src={item.profileImageUrl}
              alt={item.name}
              className="h-14 w-14 rounded-full"
            />
            <p className="text-center">{item.name}</p>
          </div>
        ))}
    </div>
  );
}

export default StoryList;
