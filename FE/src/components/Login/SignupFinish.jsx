import { Link } from "react-router-dom";
import welcomCat from "/imgs/signupFinish.png";

function SignupFinish() {
  return (
    <div className="gap-4 sub-layer">
      <p className="text-3xl  font-cusFont6 text-cusColor1">환영합니다.</p>
      <img src={welcomCat} alt="" />
      <p className="signup-font">회원가입이 완료되었습니당</p>
      <Link
        to={"/"}
        className="common-btn h-full max-h-[50px] w-full max-w-[284px]"
      >
        <button>홈 화면으로 이동</button>
      </Link>
    </div>
  );
}

export default SignupFinish;
