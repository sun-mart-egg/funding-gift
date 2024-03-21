import LoginLogo from "/imgs/loginLogo.png";
import KakaoLogin from "/imgs/kakaoLogin.png";

import { Link } from "react-router-dom";

function Login() {
  return (
    <>
      <img src={LoginLogo} alt="login" />
      <p className="signup-font m-5">소셜 계정으로 로그인</p>
      <img src={KakaoLogin} alt="login" className=" mt-7" />
      <p className="signup-font m-5">or</p>
      <button className="common-btn signup-font h-full max-h-[50px] w-full max-w-[284px] text-white">
        판매자 계정으로 로그인
      </button>
      <Link to={"/signup"}>
        <p className="m-5 underline font-cusFont2 text-cusColor1">
          아직 회원이 아니신가요?
        </p>
      </Link>
    </>
  );
}

export default Login;
