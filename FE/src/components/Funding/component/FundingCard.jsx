import React from "react";
import ProgressBar from "./ProgressBar";
function FundingCard({ people, title, progress, name, date, img }) {
  return (
    <div className="my-2 flex h-36 w-[85%] items-center justify-center rounded-md border border-gray-400 font-cusFont3 shadow-md">
      <div>
        <img src={img} alt="" className="w-28 pl-1" />
      </div>
      <div className="flex w-[60%] flex-col items-center justify-center text-[10px]">
        <p className="">{people}의</p>
        <p className=" font-cusFont1 text-[18px]">{title}</p>
        <div className="my-1 h-[12px] w-4/5">
          <ProgressBar progress={progress} />
        </div>
        <p>{name}</p>
        <p>{date}</p>
      </div>
    </div>
  );
}

export default FundingCard;
