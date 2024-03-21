// 이미지 import
import SignupLogo from '/imgs/signupLogo.png'
import { Link } from 'react-router-dom';
import { useState } from 'react';
import DaumPostcodeEmbed from 'react-daum-postcode';


function Signup() {
    const [address, setAddress] = useState({
        address: "",
        postcode: ""
    })

    const handleAddress = (data) => {
        setAddress({
            address: data.address,
            postcode: data.zonecode
        })
    }
    
    return (
        <div className="main-layer">
            <img src={SignupLogo} alt="회원가입로고" className=" top-[60px]" />
            <p className='login-font'>물건을 받을 주소를 입력해주세용</p>

            <div className='flex flex-row items-center w-[330px] gap-5'>
                {/* value 값을 통해 FindAddress에서 받아온 postcode를 할당 */}
                {/* 읽기 전용 설정을 통해 주소 직접 입력을 제한함 */}
                <input type="text" placeholder='우편번호' className='w-[100px] h-[50px] border-[2.5px] rounded-[5px] p-3' value={address.postcode} readOnly/>
                <button className="w-[100px] h-[30px] rounded bg-gray-400 text-white text-[15px]">우편번호 찾기</button>
            </div>

            <div className="flex flex-row w-[330px] h-[50px] border-[2.5px] rounded-[5px] p-3">
                {/* value 값을 통해 FindAddress에서 받아온 address를 할당 */}
                {/* 읽기 전용 설정을 통해 주소 직접 입력을 제한함 */}
                <input type="text" placeholder="주소" className='w-full' value={address.address} readOnly/>
            </div>

            <div className=' flex flex-row justify-between w-[330px] h-[50px] border-[2.5px] rounded-[5px] p-3'>
                <input type="text" placeholder='상세주소' />
            </div>

            <Link to={"/signup/favorite"}>
                <button className="common-btn">다음</button>
            </Link>

            {/* 주소 찾으면 input 태그에 value 할당되고 검색창 자동으로 닫아짐 */}
            <DaumPostcodeEmbed onComplete={handleAddress} autoClose />
        </div>
    )
}

export default Signup;