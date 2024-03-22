function SignupFinish() {
  return (
    <div className=" flex h-[640px] w-[360px] flex-col items-center bg-white">
      <p className=" font-cusFont6 text-3xl text-cusColor1">환영합니다.</p>
      <div className=" h-[220px] w-[360px] bg-cyan-300" />
      <p className=" font-cusFont3 text-[15px]">회원가입이 완료되었습니당</p>
      <button className=" h-[48px] w-[285px] rounded-lg bg-cusColor3 font-cusFont2 text-white">
        로그인 화면으로 이동
      </button>
    </div>
  );
}

export default SignupFinish;
