import { Link } from "react-router-dom"
import SignupLogo from "/imgs/signupLogo.png"

function Favorite() {
    return (
        <div className="main-layer">
            <img src={SignupLogo} alt="회원가입로고" className="mt-20 "/>
            <p className="login-font">관심이 있는 물건을 최대 5개 선택하세용</p>
            <div className="grid grid-flow-col grid-rows-3 gap-4 m-4">
                <button className="fav-btn">노트북</button>
                <button className="fav-btn">닌텐도</button>
                <button className="fav-btn">에어팟 맥스</button>
                <button className="fav-btn">백팩</button>
                <button className="fav-btn">애완동물</button>
                <button className="fav-btn">애플워치</button>
            </div>
            <Link to={"/signupFin"}>
                <button className="common-btn">선택 완료</button>
            </Link>
        </div>
    )
}
export default Favorite