import ProgressBar from "./ProgressBar";
import Egg from "../../../../public/imgs/egg3.jpg";

function FundingDetailInfo({
  friendName,
  title,
  name,
  price,
  detail,
  progress,
  img,
}) {
  return (
    <div className=" flex flex-col items-center justify-center ">
      <div id="fundingTitle " className="mb-4 flex flex-col items-center ">
        {friendName == null ? "" : <p>{friendName}님의 </p>}
        <p className="font-cusFont1 text-[30px]">{title}</p>
      </div>
      <div
        id="fundingInfoSection"
        className="flex-items  flex items-center justify-center px-4"
      >
        <div id="fundingImg" className="w-[40%]">
          <img src={img} alt="" />
        </div>
        <div id="fundingInfo" className="w-3/6 pl-4">
          <p className="text-[18px] ">{name}</p>
          <div className="flex-items mt-2 flex items-center  justify-between text-[12px]">
            <p className="pt-2 ">{price} 원 </p>{" "}
            <button className="rounded-md bg-gray-400 px-2 py-1 text-[10px] text-white">
              배송 조회
            </button>
          </div>

          <p className="pt-2 text-[12px]">{detail}</p>
        </div>
      </div>
      <div className="m-8 h-[20px] w-[85%]">
        <ProgressBar progress={progress} />
      </div>
    </div>
  );
}

export default FundingDetailInfo;
