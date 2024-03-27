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
      <button
        className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white"
        onClick={handleSelectButtonClick}
      >
        기념일 선택하기
      </button>
    </div>
  );
}

export default AnniversaryListPage;
