import BackBtn from "/imgs/backbtn.png"
import Logo from "/imgs/logo.png"
import { Link, useNavigate } from "react-router-dom"

function SubHeader() {
    const navigate = useNavigate()
    const handleBack = () => {
        navigate(-1) // 뒤로가기
    }

    return (
        <div className="items-center p-4 header-layer">
            <button>
                <img src={BackBtn} alt="뒤로가기" onClick={handleBack} />
            </button>

            <Link to={"/"}>
                <img src={Logo} alt="메인로고" />
            </Link>
            <div className="w-[20px] h-[20px] bg-transparent"></div>
        </div>
    )
}

export default SubHeader