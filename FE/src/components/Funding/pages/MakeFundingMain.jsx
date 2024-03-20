import { FaHeart } from "react-icons/fa6";
import { FaCartShopping } from "react-icons/fa6";

function MakeFundingMain() {
  return (
    <div className="flex h-screen items-center justify-center bg-cusColor3">
      <div
        id="makeCard"
        className="flex h-3/5 w-2/3 flex-col justify-around rounded-xl bg-white p-4"
      >
        <div id="titleSection" className="mb-8 text-center">
          <p>생선가게에서 </p>
          <p>놀라운 선물을 만들어 보세요</p>
        </div>
        <div id="buttonSection" className="flex w-full justify-around">
          <button
            style={{ flexBasis: "40%" }}
            className="rounded-lg bg-cusColor3"
          >
            {" "}
            <FaHeart /> 위시에서 선물 만들기
          </button>
          <button
            style={{ flexBasis: "40%" }}
            className="rounded-lg bg-cusColor4"
          >
            {" "}
            <FaCartShopping />
            쇼핑몰에서 선물 만들기
          </button>
        </div>
      </div>
    </div>
  );
}

export default MakeFundingMain;
