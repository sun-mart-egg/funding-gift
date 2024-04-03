import ProgressBar from "./ProgressBar";

function FundingDetailInfo({
  friendName,
  title,
  name,
  price,
  detail,
  progress,
  fundingStatus,
  startDate,
  endDate,
  img,
}) {
  return (
    <div className=" flex flex-col items-center justify-center ">
      <div id="fundingTitle " className="mb-4 flex flex-col items-center ">
        <div className=" pb-1 font-cusFont3 text-[12px]">
          {fundingStatus === "IN_PROGRESS" ? (
            <div className="flex flex-col items-center justify-center ">
              <div className="font-cusFont5 text-[14px] text-cusColor2">
                진행중인 펀딩
              </div>
              <div>
                {startDate} ~ {endDate}
              </div>
            </div>
          ) : (
            <div className="flex flex-col items-center justify-center ">
              <div className="font-cusFont5 text-[14px] text-cusColor3">
                진행 전인 펀딩
              </div>
              <div>
                {startDate} ~ {endDate}
              </div>
            </div>
          )}
        </div>

        {friendName == null ? "" : <p>{friendName}님의 </p>}
        <p className="font-cusFont1 text-[30px]">{title}</p>
      </div>
      <div
        id="fundingInfoSection"
        className="flex-items  flex items-center justify-center px-4"
      >
        <div id="fundingImg" className="w-[40%]">
          <img src={img} alt="" className="rounded-md" />
        </div>
        <div id="fundingInfo" className="w-3/6 pl-4">
          <p className="text-[18px] ">{name}</p>
          <div className="flex-items mt-2 flex items-center  justify-between text-[12px]">
            <p className="pt-2 ">{price} 원 </p>{" "}
          </div>

          <p className="pt-2 text-[12px]">{detail}</p>
        </div>
      </div>

      <div className="m-6 h-[20px] w-[85%]">
        <ProgressBar progress={progress} />
      </div>
    </div>
  );
}

export default FundingDetailInfo;
