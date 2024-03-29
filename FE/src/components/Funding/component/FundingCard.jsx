import React from "react";
import ProgressBar from "./ProgressBar";

function FundingCard({ people, title, progress, name, date, img }) {
  return (
    <div className="my-2 flex h-40 w-full flex-col rounded-md font-cusFont2">
      <div id="head" className=" my-1 flex items-center justify-start pl-3">
        <img
          src="/imgs/egg3.jpg"
          alt=""
          className=" mr-2 h-8 rounded-full object-cover"
          style={{ width: "auto" }}
        />
        <div>{people}</div>
      </div>
      <div className="my-2 flex w-full items-center justify-center rounded-md font-cusFont3">
        <div>
          <img src={img} alt="" className="w-28 rounded-md object-cover " />
        </div>
        <div className="flex w-[60%] flex-col items-center justify-center text-[10px]">
          <p className="font-cusFont1 text-[18px]">{title}</p>
          <div className="my-1 h-[12px] w-4/5">
            <ProgressBar progress={progress} />
          </div>
          <p>{name}</p>
          <p>{date}</p>
        </div>
      </div>
    </div>
  );
}

export default FundingCard;
