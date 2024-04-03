import ProgressBar from "./ProgressBar";
import { useNavigate } from "react-router-dom";

function Card({
  img,
  title,
  productName,
  startDate,
  endDate,
  progress,
  fundingId,
  basePath,
}) {
  const navigate = useNavigate();
  return (
    <div
      onClick={() => navigate(`${basePath}/${fundingId}`)}
      className="m-1 mx-2 w-[45%] bg-white"
    >
      <div className="product_img_div mt-3 flex justify-center pb-2">
        {/* 너비를 지정하고 object-fit: cover를 추가합니다. */}
        <img
          src={img}
          alt=""
          className="h-[180px] w-full rounded-lg object-cover"
        />
      </div>
      <p className="product_title font-cusFont1 text-[14px]">{title}</p>
      <div className="h-[14px]">
        <ProgressBar progress={progress} />
      </div>
      <div className="pt-2">
        <p className="product_des pb-1 text-[10px]">{productName}</p>
        <div className="product_mon text-[10px]">
          {startDate} ~ {endDate}
        </div>
      </div>
    </div>
  );
}

export default Card;
