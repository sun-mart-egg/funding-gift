import { FaHeart } from "react-icons/fa6";
import { FaCartShopping } from "react-icons/fa6";
import backgroundURL from "../../../../public/imgs/background.PNG";
import { useNavigate } from "react-router-dom";

function MakeFundingMain() {
  const navigate = useNavigate();
  return (
    <div
      className="flex h-screen w-full items-center justify-center"
      style={{
        backgroundImage: `url(${backgroundURL})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
    >
      <div
        id="makeCard"
        className="flex h-3/5 w-[75%] flex-col justify-evenly rounded-xl bg-white "
      >
        <div
          id="titleSection"
          className="mb-8 text-center font-cusFont5 text-[18px]"
        >
          <p>생선가게에서 </p>
          <p>놀라운 선물을 만들어 보세요</p>
        </div>
        <div id="buttonSection" className="flex w-full justify-around">
          <button
            style={{ flexBasis: "50%" }}
            className="mx-2 flex flex-col items-center justify-center rounded-lg bg-cusColor3 p-1 py-2 text-white"
          >
            <FaHeart />
            <p className="text-[10px]">위시에서 고르기</p>
          </button>
          <button
            onClick={() => navigate("/product")}
            style={{ flexBasis: "50%" }}
            className=" mx-2 flex flex-col  items-center justify-center rounded-lg bg-cusColor4 text-white"
          >
            {" "}
            <FaCartShopping />
            <p className="text-[10px]">쇼핑몰에서 고르기</p>
          </button>
        </div>
      </div>
    </div>
  );
}

export default MakeFundingMain;
