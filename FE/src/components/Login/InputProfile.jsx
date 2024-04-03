import SignupLogo from "/imgs/signupLogo.png";
import useUserStore from "../Store/UserStore";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useState } from "react";

function InputProfile() {
  const updateUserStore = useUserStore((state) => state.updateUserStore);
  const myName = useUserStore((state) => state.name);
  const myGender = useUserStore((state) => state.gender);
  const [myBirthDay, setMyBirthday] = useState("");
  const myEmail = useUserStore((state) => state.email);
  const myPhoneNumber = useUserStore((state) => state.phoneNumber);
  const navigate = useNavigate();

  // 데이터 유효성 검사를 위한 useState
  const [nameErr, setNameErr] = useState("");
  const [genderErr, setGenderErr] = useState("");
  const [birthErr, setBirthErr] = useState("");
  const [emailErr, setEmailErr] = useState("");
  const [phoneNumErr, setPhoneNumErr] = useState("");
  const [totalErr, setTotalErr] = useState("")

  // 이름 input에 입력하는 값을 store의 name에 저장
  const handleName = (event) => {
    const inputName = event.target.value;
    updateUserStore("name", inputName);
    if (inputName.length < 1 || inputName.length > 10) {
      setNameErr("이름은 1자 이상 10자 이하이어야 합니다.");
    } else {
      setNameErr("");
    }
  };

  // e-mail input에 입력하는 값을 store의 email에 저장
  const handleEmail = (event) => {
    const inputEmail = event.target.value;
    updateUserStore("email", inputEmail);

    // 이메일 형식 검사 정규식
    const patternEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!patternEmail.test(inputEmail) || inputEmail > 50) {
      setEmailErr("이메일을 올바르게 입력해주세요.");
    } else {
      setEmailErr("");
    }
  };

  // 생년월일 input에 입력하는 값을 store의 birthday에 저장
  const handleBirthday = (event) => {
    const inputBirthday = event.target.value;
    setMyBirthday(inputBirthday);
    if (!/^\d{0,8}$/.test(inputBirthday) || inputBirthday.length !== 8) {
      setBirthErr("생년월일을 올바르게 입력해주세요.");
    } else {
      setBirthErr("");
    }
  };

  // 연락처 input 에 입력하는 값을 store의 phoneNumber에 저장
  const handlePhoneNumber = (event) => {
    const inputPhoneNum = event.target.value;
    updateUserStore("phoneNumber", inputPhoneNum);

    // 전화번호 형식 검사 정규식
    const patternPhoneNum = /^\d{10,11}$/;
    if (!patternPhoneNum.test(inputPhoneNum)) {
      setPhoneNumErr("연락처를 올바르게 입력해주세요.");
    } else {
      setPhoneNumErr("");
    }
  };

  const handleGender = (genderValue) => {
    updateUserStore("gender", genderValue);
    if (!genderValue) {
      setGenderErr("성별을 선택해주세요");
    } else {
      setGenderErr("");
    }
  };

  const handleNextSignupPage = () => {
    // if (nameErr === "" && genderErr === "" && birthErr === "" && emailErr === "" && phoneNumErr === "" &&
    // myName && myGender && myBirthDay && myEmail && myPhoneNumber) {
    const birthyear = myBirthDay.substring(0, 4);
    const birthday = myBirthDay.substring(4);
    axios
      .put(
        import.meta.env.VITE_BASE_URL + "/api/consumers",
        {
          "name": myName,
          "email": myEmail,
          "phoneNumber": myPhoneNumber,
          "birthyear": birthyear,
          "birthday": birthday,
          "gender": myGender,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("access-token")}`,
          },
        },
      )
      .then((res) => {
        console.log(res);
        console.log("정보 수정 성공");
        navigate("/signup");
      })
      .catch((err) => {
        console.error(err);
        console.log("정보 수정 실패");
        setTotalErr("모든 정보를 올바르게 입력해주세요.")
      });
    // }
  };

  return (
    <div className="sub-layer top-[60px] gap-4">
      <img src={SignupLogo} alt="회원가입로고" className="m-3" />
      <div className="flex w-[330px] justify-between">
        <input
          type="text"
          placeholder="이름을 입력해주세요."
          className="common-btn signup-font h-[50px] w-[160px] border-[2.5px] bg-white p-3"
          value={myName}
          onChange={handleName}
          required
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

      {nameErr && <p className="signup-font text-red-500">{nameErr}</p>}
      {genderErr && <p className="signup-font text-red-500">{genderErr}</p>}

      <input
        type="text"
        placeholder="생년/월/일 8자리를 입력해주세요"
        className="common-btn signup-font h-[50px] w-[330px] border-[2.5px] bg-white p-3"
        value={myBirthDay}
        onChange={handleBirthday}
        required
      />
      {birthErr && <p className="signup-font text-red-500">{birthErr}</p>}

      <input
        type="text"
        placeholder="이메일을 입력해주세요."
        className="common-btn signup-font h-[50px] w-[330px] border-[2.5px] bg-white p-3"
        value={myEmail}
        onChange={handleEmail}
        required
      />
      {emailErr && <p className="signup-font text-red-500">{emailErr}</p>}

      <input
        type="number"
        placeholder="연락처를 입력해주세요."
        className="common-btn signup-font h-[50px] w-[330px] border-[2.5px] bg-white p-3"
        value={myPhoneNumber}
        onChange={handlePhoneNumber}
        required
      />
      {phoneNumErr && <p className="signup-font text-red-500">{phoneNumErr}</p>}

      <button
        className="common-btn h-full max-h-[50px] w-full max-w-[284px]"
        onClick={handleNextSignupPage}
      >
        다음
      </button>
      {totalErr && <p className="signup-font text-red-500">{totalErr}</p>}
    </div>
  );
}

export default InputProfile;
