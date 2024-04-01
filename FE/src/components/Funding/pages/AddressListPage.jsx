import AddressList from "../component/AddressList";
import { useStore } from "../../Store/MakeStore";
import { useNavigate } from "react-router-dom"; // useNavigate 사용

function AddressListPage() {
  const navigate = useNavigate();
  const setContentIndex = useStore((state) => state.setContentIndex);

  const data = [
    {
      name: "신시은",
      phoneNumber: "010-4948-7118",
      defaultAddr: "아슬란",
      detailAddr: "502호",
      zipCode: "39432",
      isDefault: true,
      nickname: "마이구미",
    },
    {
      name: "박창준",
      phoneNumber: "010-2669-8294",
      defaultAddr: "무지개빌",
      detailAddr: "302호",
      zipCode: "111111",
      isDefault: false,
      nickname: "창준쓰집",
    },
  ];

  const handleSelectButtonClick = () => {
    setContentIndex(3); // 컨텐츠 인덱스를 3으로 설정
    navigate("/make-funding-detail"); // 라우트 경로는 실제 경로에 맞게 조정해야 합니다.
  };

  return (
    <div className="sub-layer font-cusFont3 text-[14px]">
      <AddressList listData={data} />
      <div
        id="buttonSection"
        className="absolute bottom-0 flex w-full items-center justify-center space-x-4 pb-5"
      >
        <button
          className="  common-btn border-cus  h-[45px] w-[45%] border border-cusColor3 bg-white text-black "
          onClick={() => navigate("/new-address")}
        >
          배송지 추가
        </button>
        <button
          className="  h-[45px] w-[45%]  rounded-md bg-cusColor3 text-white"
          onClick={handleSelectButtonClick}
        >
          배송지 선택
        </button>
      </div>
    </div>
  );
}

export default AddressListPage;
