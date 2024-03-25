import { useEffect } from "react"
import { useNavigate, useSearchParams } from "react-router-dom"

function LoginCallback() {
    const [ searchParams ] = useSearchParams()
    const navigate = useNavigate()

    useEffect(() => {
        async function getTokens() {
            const accessToken = await searchParams.get("access-token")
            const memberId = await searchParams.get("member-id")

            // 토큰값이 null 값이면
            // localStorage에 accessToken, memberId를 위에 get으로 얻은걸로 할당
            // 상세주소 입력하는 signup 페이지로 이동
            if (accessToken === null) {
                localStorage.setItem("accessToken", accessToken)
                localStorage.setItem("memberId", memberId)
                console.log("토큰 : ", accessToken)
                console.log("멤버 ID : ", memberId)
                navigate("/signup")
            }
            // 토큰이 있는 경우 == 이미 로그인을 한 경우
            else {
                navigate("/")
            }
        }
        getTokens()
    })
    return 
}

export default LoginCallback