import ProgressBar from "./ProgressBar";
import { useNavigate } from "react-router-dom";
import Egg from "../../../../public/imgs/egg3.jpg";

function Card({ title, name, date, progress }) {
  const navigate = useNavigate();
  return (
    <div
      onClick={() => navigate("/friend-funding-detail")}
      className="m-2 w-[45%] rounded-lg border-2 bg-white p-4 shadow-md"
    >
      <div className="product_img_div flex justify-center pb-2">
        <img src={Egg} alt="" className="size-3/4" />
      </div>
      <p className="product_title font-cusFont1 text-[14px]">{title}</p>
      <div className="h-[14px]">
        <ProgressBar progress={progress} />
      </div>
      <div className="pt-2">
        <p className="product_des pb-1 text-[10px]">{name}</p>
        <div className="product_mon  text-[10px]">{date}</div>
      </div>
    </div>
  );
}

export default Card;
