import LoginLogo from "/imgs/loginLogo.png";
import KakaoLogin from "/imgs/kakaoLogin.png";

import { Link } from "react-router-dom";

function Login() {
  const REACT_APP_KAKAO_URL =
    'http://localhost:8081/oauth2/authorization/kakao?redirect_uri=http://localhost:5173/login/oauth2/code/kakao&mode=login'

  const kakaoLoginHandler = () => {
    // window.location.href = process.env.REACT_APP_KAKAO_URL;
    window.location.replace(REACT_APP_KAKAO_URL);
  };

  return (
    <div className="sub-layer">
      <img src={LoginLogo} alt="login" />
      <p className="signup-font m-5">소셜 계정으로 로그인</p>
      <button onClick={kakaoLoginHandler}>
        <img src={KakaoLogin} alt="login" className=" mt-7" />
      </button>
      <p className="signup-font m-5">or</p>
      <button className="common-btn signup-font h-full max-h-[50px] w-full max-w-[284px] text-white">
        판매자 계정으로 로그인
      </button>
      <Link to={"/signup"}>
        <p className="m-5 font-cusFont2 text-cusColor1 underline">
          아직 회원이 아니신가요?
        </p>
      </Link>
    </div>
  );
}

export default Login;
