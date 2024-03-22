// 이미지 import
import SignupLogo from "/imgs/signupLogo.png";
import { Link } from "react-router-dom";
import { useState } from "react";
import DaumPostcodeEmbed from "react-daum-postcode";

function Signup() {
  // 주소를 저장할 상태변수
  const [address, setAddress] = useState({
    address: "",
    postcode: "",
  });

  // 상세주소 검색창 on/off 상태변수
  const [isOpen, setIsOpen] = useState(false)

  // 주소 관련 상태함수에 주소, 우편번호 data 할당
  const handleAddress = (data) => {
    setAddress({
      address: data.address,
      postcode: data.zonecode,
    });
    setIsOpen(false)
  };

  return (
    <div className="sub-layer">
      <img src={SignupLogo} alt="회원가입로고" className="m-3" />
      <p className="signup-font">물건을 받을 주소를 입력해주세용</p>

      <div className="flex w-[330px] flex-row items-center gap-5">
        {/* value 값을 통해 FindAddress에서 받아온 postcode를 할당 */}
        {/* 읽기 전용 설정을 통해 주소 직접 입력을 제한함 */}
        <input
          type="text"
          placeholder="우편번호"
          className="p-3 bg-white border-[2.5px] common-btn signup-font w-[100px] h-[50px]"
          value={address.postcode}
          readOnly
        />
        <button className="h-[30px] w-[100px] rounded bg-gray-400 text-[15px] text-white"
        onClick={() => setIsOpen(true)}>
          우편번호 찾기
        </button>
      </div>

      <div className="flex h-[50px] w-[330px] flex-row rounded-[5px] border-[2.5px] p-3">
        {/* value 값을 통해 FindAddress에서 받아온 address를 할당 */}
        {/* 읽기 전용 설정을 통해 주소 직접 입력을 제한함 */}
        <input
          type="text"
          placeholder="주소"
          className="w-full signup-font"
          value={address.address}
          readOnly
        />
      </div>

      <div className=" flex h-[50px] w-[330px] flex-row justify-between rounded-[5px] border-[2.5px] p-3">
        <input type="text" placeholder="상세주소" className="signup-font"/>
      </div>

      <Link to={"/signupFin"} className="common-btn max-w-[284px] max-h-[50px] w-full h-full">
        <button>다음</button>
      </Link>

      {/* 주소 찾으면 input 태그에 value 할당되고 검색창 자동으로 닫아짐 */}
      {isOpen && 
      <div className="absolute flex flex-col items-center justify-center max-w-[400px] w-full h-[500px] border-2 gap-4 bg-white">
        <DaumPostcodeEmbed onComplete={handleAddress} autoClose />
        <button className="common-btn max-w-[284px] max-h-[50px] w-full h-full" onClick={() => setIsOpen(false)}>닫기</button>
      </div>
      }
    </div>
  );
}

export default Signup;
