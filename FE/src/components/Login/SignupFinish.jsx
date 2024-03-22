import { Link } from "react-router-dom";
import welcomCat from "/imgs/signupFinish.png"

function SignupFinish() {
  return (
    <div className="sub-layer">
      <p className=" font-cusFont6 text-3xl text-cusColor1">환영합니다.</p>
      <img src={welcomCat} alt="" />
      <p className="signup-font">회원가입이 완료되었습니당</p>
      <Link to={"/login"} className="common-btn max-w-[284px] w-full max-h-[50px] h-full">
        <button>로그인 화면으로 이동</button>
      </Link>
    </div>
  );
}

export default SignupFinish;
