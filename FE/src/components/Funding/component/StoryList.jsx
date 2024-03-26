import React, { useState } from "react";

function StoryList({ listData }) {
  const [data, setData] = useState(listData);

  return (
    <div
      id="storyList"
      className="flex w-full justify-center overflow-x-scroll  font-cusFont3 text-[12px]"
    >
      {data.map((item, index) => (
        <div
          key={index}
          className="flex-none flex-col items-center justify-center p-4"
        >
          <img
            src={item.img}
            alt={item.people}
            className="h-14 w-14 rounded-full" // 너비와 높이를 24로 설정
          />
          <p className="text-center">{item.people}</p>
        </div>
      ))}
    </div>
  );
}

export default StoryList;
