import { useNavigate } from "react-router-dom"; // useNavigate 사용
import { FaCheckCircle } from "react-icons/fa";

function MakeFundingFinish() {
  const navigate = useNavigate(); // useNavigate 훅 사용

  const navigateHome = () => {
    // navigate("/make-funding-finish");
  };
  const navigateMypage = () => {
    navigate("/my-funding");
  };
  return (
    <div
      className="sub-layer font-cusFont3"
      style={{
        background: "linear-gradient(to bottom, #E5EEFF, #FFFFFF)", // 세로 그라디언트 정의
      }}
    >
      <div
        id="makeCard"
        className="mt-20 flex w-[75%] flex-col items-center justify-start rounded-xl bg-white p-4 shadow-md"
        style={{ height: "68%" }}
      >
        <div
          id="contentSection"
          className="m-auto flex flex-col items-center justify-center"
        >
          <p className="mb-2 font-cusFont2 text-[18px]">
            펀딩을 성공적으로 등록했습니다!
          </p>
          <p className="font-cusFont3">지금 바로 확인해 보세요!</p>
          <FaCheckCircle className="mt-2 size-6 text-cusColor1" />
        </div>
      </div>
      <div
        id="buttonSection"
        className="absolute bottom-0 w-full justify-around pb-5"
      >
        <div className="flex justify-center space-x-4">
          <button
            onClick={() => navigate("/")}
            style={{ width: "calc(40% - 10px)" }} // 버튼 너비 조정
            className="common-btn border-cus border border-cusColor3 bg-white text-black "
          >
            홈화면으로
          </button>
          <button
            onClick={() => navigate("/my-funding")}
            style={{ width: "calc(40% - 10px)" }} // 버튼 너비 조정
            className="common-btn"
          >
            내 펀딩화면으로{" "}
          </button>
        </div>
      </div>
    </div>
  );
}

export default MakeFundingFinish;
