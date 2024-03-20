import ProgressBar from "./ProgressBar";

function FundingDetailInfo({
  friendName,
  title,
  name,
  price,
  detail,
  progress,
}) {
  return (
    <div className="flex flex-col items-center justify-center ">
      <div id="fundingTitle">
        <p>{friendName}님의 </p>
        <h1>{title}</h1>
      </div>
      <div id="fundingInfoSection" className="flex items-center p-4">
        <div id="fundingImg">
          <img src="../../../../public/imgs/egg3.jpg" alt="" />
        </div>
        <div id="fundingInfo" className="p-4">
          <p>{name}</p>
          <p>{price}</p>
          <p>{detail}</p>
          <button>배송 조회</button>
        </div>
      </div>
      <ProgressBar progress={progress} />
    </div>
  );
}

export default FundingDetailInfo;
