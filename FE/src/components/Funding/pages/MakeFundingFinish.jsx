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
    <div className="h-screen flex-col items-center justify-center bg-slate-300">
      <div
        id="makeCard"
        className="flex h-3/5 w-2/3 flex-col justify-around rounded-xl bg-white p-4"
      >
        <div id="contentSection">펀딩 성공적으로 만들었음 ㅊㅋㅊㅋ</div>
      </div>
      <div id="buttonSection" className="flex w-full justify-around">
        <button className="rounded-lg bg-cusColor3">홈화면으로</button>
        <button onClick={navigateMypage} className="rounded-lg bg-cusColor4">
          내 펀딩화면으로{" "}
        </button>
      </div>
    </div>
  );
}

export default MakeFundingFinish;
