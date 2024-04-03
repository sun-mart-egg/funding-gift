import React, { useState, useEffect } from "react";
import FundingCard from "./FundingCard";

function FundingList({ listData }) {
  const [data, setData] = useState([]);
  return (
    <div
      id="fundingList"
      className="w-full flex-col items-center justify-center overflow-auto"
    >
      {Array.isArray(listData) &&
        listData.map((item, index) => (
          <div key={index} className="flex w-full justify-center">
            <FundingCard
              fundingId={item.fundingId}
              profileImg={item.profileImageUrl}
              people={item.consumerName}
              title={item.title}
              progress={(item.sumPrice / item.targetPrice) * 100}
              name={item.productName}
              date={item.anniversaryDate}
              img={item.productImage}
              startDate={item.startDate}
              endDate={item.endDate}
              anniversaryCategoryName={item.anniversaryCategoryName}
            />
          </div>
        ))}
    </div>
  );
}

export default FundingList;
