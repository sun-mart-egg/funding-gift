import AddressList from "../component/AddressList";
import { useStore } from "../../Store/MakeStore";
import { useNavigate } from "react-router-dom"; // useNavigate 사용
import { getAddressList } from "../api/AddressAPI";
import { useEffect, useState } from "react";

function AddressListPage() {
  const navigate = useNavigate();
  const setContentIndex = useStore((state) => state.setContentIndex);

  const [addressList, setAddressList] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("access-token");
    if (token) {
      getAddressList(token, setAddressList);
    }
  }, [addressList]);

  useEffect(() => {
    console.log(addressList);
  }, [addressList]);

  const handleSelectButtonClick = () => {
    setContentIndex(3); // 컨텐츠 인덱스를 3으로 설정
    navigate("/make-funding-detail"); // 라우트 경로는 실제 경로에 맞게 조정해야 합니다.
  };

  if (!addressList) return <div>주소 목록 로딩 중</div>;
  return (
    <div className="sub-layer font-cusFont3 text-[14px]">
      <AddressList listData={addressList} />
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
