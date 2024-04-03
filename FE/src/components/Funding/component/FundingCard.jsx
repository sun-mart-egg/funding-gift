import React from "react";
import ProgressBar from "./ProgressBar";
import { Navigate, useNavigate } from "react-router";
function FundingCard({
  people,
  title,
  progress,
  name,
  date,
  img,
  startDate,
  endDate,
  anniversaryCategoryName,
  profileImg,
  fundingId,
}) {
  const navigate = useNavigate(); // useNavigate 훅 사용

  const handleNavigate = (id) => {
    navigate(`/friend-funding-detail/${id}`);
  };
  return (
    <div
      onClick={() => handleNavigate(fundingId)}
      className="h-50 my-2 flex w-full flex-col rounded-md   pb-4 font-cusFont2"
    >
      <div
        id="head"
        className=" my-1 flex items-center justify-between pb-1 pl-3 text-[13px]"
      >
        <div className="flex items-center justify-center">
          <img
            src={profileImg}
            alt=""
            className="mr-2 h-9 rounded-full object-cover"
            style={{ width: "30px", height: "30px" }}
          />
          <div className="pr-2">{people}</div>
        </div>
        <div className="flex items-center justify-center">
          <div className="pr-2">{date}</div>
          <div className="pr-4">{anniversaryCategoryName}</div>
        </div>
      </div>
      <div className="my-2 flex w-full items-center justify-evenly rounded-md px-2 font-cusFont3">
        <div>
          <img
            src={img}
            alt=""
            className="rounded-md object-cover"
            style={{ width: "120px", height: "120px" }} // 여기에서 이미지 크기를 지정
          />
        </div>
        <div className="flex w-[60%] flex-col items-center justify-center text-[10px]">
          <p className="mx-4 font-cusFont1 text-[20px]">{title}</p>
          <div className="my-1 h-[12px] w-4/5">
            <ProgressBar progress={progress} />
          </div>
          <p className="p-2">{name}</p>
          <p>
            {startDate} ~ {endDate}
          </p>
        </div>
      </div>
    </div>
  );
}

export default FundingCard;
