import { useNavigate } from "react-router-dom"; // useNavigate 사용

function MakeFundingFinish() {
    const navigate = useNavigate(); // useNavigate 훅 사용

    const navigateHome = () => {
        // navigate("/make-funding-finish");
    };
    const navigateMypage = () => {
        navigate("/my-funding");
    };
    return (
        <div className="bg-slate-300 h-screen flex-col justify-center items-center">
            <div id="makeCard" className="w-2/3 h-3/5 rounded-xl bg-white p-4 flex flex-col justify-around">
                <div id="contentSection">펀딩 성공적으로 만들었음 ㅊㅋㅊㅋ</div>
            </div>
            <div id="buttonSection" className="flex justify-around w-full">
                <button className="bg-cusColor3 rounded-lg">홈화면으로</button>
                <button onClick={navigateMypage} className="bg-cusColor4 rounded-lg">
                    내 펀딩화면으로{" "}
                </button>
            </div>
        </div>
    );
}

export default MakeFundingFinish;
