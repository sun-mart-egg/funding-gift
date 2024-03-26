import React, { useState, useEffect } from "react";
import FundingCard from "./FundingCard";

function FundingList({ listData }) {
  const [data, setData] = useState(listData);

  useEffect(() => {
    setData(listData);
  }, [listData]);

  return (
    <div
      id="fundingList"
      className="w-full flex-col items-center justify-center overflow-auto"
    >
      {data.map((item, index) => (
        <div className="flex w-full justify-center">
          <FundingCard
            key={item.index}
            people={item.people}
            title={item.title}
            progress={item.progress}
            name={item.name}
            date={item.date}
            img={item.img}
          />
        </div>
      ))}
    </div>
  );
}

export default FundingList;
