// 이미지 import
import LoginLogo from '/imgs/loginLogo.png'
import KakaoLogin from '/imgs/kakaoLogin.png'
import sellerLogin from '/imgs/sellerLogin.png'

function Login() {
    return (
        <>
            <div className=" flex flex-col items-center w-[360px] h-[640px] bg-white">
                <img src={LoginLogo} alt="login" className=" mt-20" />
                <p className=" text-gray-400 m-4">소셜 계정으로 로그인</p>
                <img src={KakaoLogin} alt="login" className=" mt-7" />
                <p className=" text-gray-400 font-cusFont3 font-bold mt-7">or</p>
                <img src={sellerLogin} alt="login" className=" mt-7 left-0.5" />
                <p className=" text-cusColor1 font-semibold underline mt-5">아직 회원이 아니신가요?</p>
            </div>
        </>
    )
}

export default Login;