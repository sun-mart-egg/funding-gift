// 이미지 import
import LoginLogo from '/imgs/loginLogo.png'
import KakaoLogin from '/imgs/kakaoLogin.png'
import sellerLogin from '/imgs/sellerLogin.png'

function Login() {
    return (
        <>
            <div className="main-layer">
                <img src={LoginLogo} alt="login" className="mt-20 " />
                <p className="m-4 text-gray-400 ">소셜 계정으로 로그인</p>
                <img src={KakaoLogin} alt="login" className=" mt-7" />
                <p className="font-bold text-gray-400 font-cusFont3 mt-7">or</p>
                <img src={sellerLogin} alt="login" className=" mt-7 left-0.5" />
                <p className="mt-5 font-semibold underline text-cusColor1">아직 회원이 아니신가요?</p>
            </div>
        </>
    )
}

export default Login;