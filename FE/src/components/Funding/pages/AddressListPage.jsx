import AddressList from "../component/AddressList";
import { useStore } from "../../Store/MakeStore";
import { useNavigate } from "react-router-dom"; // useNavigate 사용

function AddressListPage() {
  const navigate = useNavigate();
  const setContentIndex = useStore((state) => state.setContentIndex);

  const data = [
    {
      name: "신시은",
      phone: "010-4948-7118",
      address: "아슬란",
      isDefault: true,
      nickname: "마이구미",
    },
    {
      name: "박창준",
      phone: "010-2669-8294",
      address: "무지개빌",
      isDefault: false,
      nickname: "창준쓰집",
    },
  ];

  const handleSelectButtonClick = () => {
    setContentIndex(3); // 컨텐츠 인덱스를 2로 설정
    navigate("/make-funding-detail"); // 라우트 경로는 실제 경로에 맞게 조정해야 합니다.
  };

  return (
    <div className="sub-layer font-cusFont3 text-[14px]">
      <AddressList listData={data} />
      <button
        onClick={handleSelectButtonClick}
        className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white"
      >
        배송지 선택하기
      </button>
    </div>
  );
}

export default AddressListPage;
