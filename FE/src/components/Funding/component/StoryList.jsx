import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function StoryList({ listData }) {
  const [data, setData] = useState(listData);
  const navigate = useNavigate();

  const handleStoryClick = (item) => {
    console.log(item);
    navigate(`/story/${item.id}`);
  };

  return (
    <div
      id="storyList"
      className="flex w-full justify-center overflow-x-scroll font-cusFont3 text-[12px]"
    >
      {data.map((item, index) => (
        <div
          id="storyItem"
          key={index}
          className="flex-none cursor-pointer flex-col items-center justify-center p-4"
          onClick={() => handleStoryClick(item)}
        >
          <img
            src={item.img}
            alt={item.people}
            className="h-14 w-14 rounded-full"
          />
          <p className="text-center">{item.people}</p>
        </div>
      ))}
    </div>
  );
}

export default StoryList;
