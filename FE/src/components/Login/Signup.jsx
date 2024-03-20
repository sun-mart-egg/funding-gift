// 이미지 import
import SignupLogo from '/imgs/signupLogo.png'


function Signup() {
    return (
        <div className=" flex flex-col items-center mt-1 w-[360px] h-[640px] bg-white">
            <img src={SignupLogo} alt="회원가입로고" className=" top-[60px]" />
            <p className='m-4 text-gray-400 font-cusFont3'>물건을 받을 주소를 입력해주세용</p>

            <div className=' flex flex-row justify-between w-[330px] h-[50px] border-[2.5px] rounded-[5px] p-3'>
                <input type="text" placeholder="주소" />
                <button className=' w-[44px] h-[24px] rounded bg-gray-400 text-white text-[15px]'>찾기</button>
            </div>

            <div className=' flex flex-row justify-between w-[330px] h-[50px] border-[2.5px] rounded-[5px] p-3'>
                <input type="text" placeholder='상세주소' />
            </div>

            <button className=' w-[285px] h-[48px] font-cusFont2 text-white rounded-lg bg-cusColor3'>다음</button>
        </div>
    )
}

export default Signup;