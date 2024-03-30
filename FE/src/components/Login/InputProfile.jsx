import SignupLogo from "/imgs/signupLogo.png";
import useUserStore from "../Store/UserStore";
import { useNavigate } from "react-router-dom";

function InputProfile() {
  const updateUserStore = useUserStore((state) => state.updateUserStore);
  const myName = useUserStore((state) => state.name);
  const myGender = useUserStore((state) => state.gender);
  const myBirthDay = useUserStore((state) => state.birthday);
  const myEmail = useUserStore((state) => state.email);
  const myPhoneNumber = useUserStore((state) => state.phoneNumber);
  const navigate = useNavigate();

  const handleName = (event) => {
    updateUserStore("name", event.target.value);
  };

  const handleEmail = (event) => {
    updateUserStore("email", event.target.value);
  };

  const handleBirthday = (event) => {
    updateUserStore("birthday", event.target.value);
  };

  const handlePhoneNumber = (event) => {
    updateUserStore("phoneNumber", event.target.value);
  };

  const handleGender = (genderValue) => {
    updateUserStore("gender", genderValue);
  };

  return (
    <div className="sub-layer gap-3">
      <img src={SignupLogo} alt="회원가입로고" className="m-3" />
      <div className="flex w-[330px] justify-between">
        <input
          type="text"
          placeholder="이름을 입력해주세요."
          className="common-btn signup-font h-[50px] w-[160px] border-[2.5px] bg-white p-3"
          value={myName}
          onChange={handleName}
        />
        <div className="flex gap-3">
          <button
            className={`common-btn ${myGender === "male" ? "bg-blue-400" : "bg-gray-400"}`}
            onClick={() => handleGender("male")}
          >
            남자
          </button>
          <button
            className={`common-btn ${myGender === "female" ? "bg-red-400" : "bg-gray-400"}`}
            onClick={() => handleGender("female")}
          >
            여자
          </button>
        </div>
      </div>

      <input
        type="text"
        placeholder="생년/월/일 8자리를 양식에 따라 입력해주세요"
        className="common-btn signup-font h-[50px] w-[330px] border-[2.5px] bg-white p-3"
        value={myBirthDay}
        onChange={handleBirthday}
      />

      <input
        type="text"
        placeholder="이메일을 입력해주세요."
        className="common-btn signup-font h-[50px] w-[330px] border-[2.5px] bg-white p-3"
        value={myEmail}
        onChange={handleEmail}
      />

      <input
        type="text"
        placeholder="연락처를 입력해주세요."
        className="common-btn signup-font h-[50px] w-[330px] border-[2.5px] bg-white p-3"
        value={myPhoneNumber}
        onChange={handlePhoneNumber}
      />

      <button
        className="common-btn h-full max-h-[50px] w-full max-w-[284px]"
        onClick={() => navigate("/signup")}
      >
        다음
      </button>
    </div>
  );
}

export default InputProfile;
