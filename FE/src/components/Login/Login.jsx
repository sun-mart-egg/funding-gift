// 이미지 import
import LoginLogo from "/imgs/loginLogo.png"
import KakaoLogin from "/imgs/kakaoLogin.png"
import sellerLogin from "/imgs/sellerLogin.png"

// 컴포넌트 import
// import Header from "../UI/Header.jsx"
// import Footer from "../UI/Footer.jsx"

function Login() {
    return (
        <>
            <div className="w-[360px] h-[640px] bg-white">
                {/* <Header className=" absolute" /> */}
                <img src={LoginLogo} alt="login" className=" relative top-[60px]" />
                <p className=" text-center text-gray-400 relative top-[80px]">소셜 계정으로 로그인</p>
                <img src={KakaoLogin} alt="login" className=" relative top-[100px]" />
                <p className=" text-center text-gray-400 font-cusFont3 font-bold relative top-[120px]">or</p>
                <img src={sellerLogin} alt="login" className=" relative top-[150px] left-0.5" />
                <p className=" text-center text-cusColor1 font-semibold underline relative top-[200px]">아직 회원이 아니신가요?</p>
            </div>
            {/* <Footer /> */}
        </>
    )
}

export default Login;