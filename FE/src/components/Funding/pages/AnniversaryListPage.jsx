import AnniversaryList from "../component/AnniversaryList";
import anniversaryData from "../data";
import { useNavigate } from "react-router-dom"; // useNavigate 사용
import { useStore } from "../../Store/MakeStore";

function AnniversaryListPage() {
  const navigate = useNavigate();
  const setContentIndex = useStore((state) => state.setContentIndex);

  const handleSelectButtonClick = () => {
    setContentIndex(2); // 컨텐츠 인덱스를 2로 설정
    navigate("/make-funding-detail"); // 라우트 경로는 실제 경로에 맞게 조정해야 합니다.
  };

  return (
    <div className="sub-layer font-cusFont3 text-[14px]">
      <AnniversaryList listData={anniversaryData} className="" />

      <div
        id="buttonSection"
        className="absolute bottom-0 flex w-full items-center justify-center space-x-4 pb-5"
      >
        <button
          className="  common-btn border-cus  h-[45px] w-[45%] border border-cusColor3 bg-white text-black "
          onClick={() => navigate("/new-anniversary")}
        >
          기념일 추가
        </button>
        <button
          className="  h-[45px] w-[45%]  rounded-md bg-cusColor3 text-white"
          onClick={handleSelectButtonClick}
        >
          기념일 선택
        </button>
      </div>
    </div>
  );
}

export default AnniversaryListPage;
