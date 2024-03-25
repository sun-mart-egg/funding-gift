import LoginLogo from "/imgs/loginLogo.png";
import KakaoLogin from "/imgs/kakaoLogin.png";

import { Link } from "react-router-dom";

function Login() {
  const REACT_APP_KAKAO_URL = import.meta.env.VITE_KAKAO_SERVER_LOGIN

  const kakaoLoginHandler = () => {
    // window.location.href = process.env.REACT_APP_KAKAO_URL;
    window.location.replace(REACT_APP_KAKAO_URL);
  };

  return (
    <div className="sub-layer">
      <img src={LoginLogo} alt="login" />
      <p className="m-5 signup-font">소셜 계정으로 로그인</p>
      <button onClick={kakaoLoginHandler}>
        <img src={KakaoLogin} alt="login" className=" mt-7" />
      </button>
      <p className="m-5 signup-font">or</p>
      <button className="common-btn signup-font h-full max-h-[50px] w-full max-w-[284px] text-white">
        판매자 계정으로 로그인
      </button>
      <Link to={"/signup"}>
        <p className="m-5 underline font-cusFont2 text-cusColor1">
          아직 회원이 아니신가요?
        </p>
      </Link>
    </div>
  );
}

export default Login;
