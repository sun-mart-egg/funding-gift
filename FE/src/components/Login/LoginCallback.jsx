import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useCookies } from "react-cookie";
function LoginCallback() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    async function getTokens() {
      // access-token, consumer-id, next-page 값을 찾아서 가져옴
      // next-page에는 값이 'main'과 'sign-up' 이 있다.
      const accessToken = await searchParams.get("access-token");
      const consumerId = await searchParams.get("consumer-id");
      const nextPage = await searchParams.get("next-page");

      // 토큰값이 null 이 아닌 경우
      // localStroage에 access-token, consumer-id를 설정
      if (accessToken !== null) {
        localStorage.setItem("access-token", accessToken);
        localStorage.setItem("consumer-id", consumerId);
        console.log("토큰 : ", accessToken);
        console.log("멤버 ID : ", consumerId);
      }

      // localStroage에서 access-token을 받아왔다면
      if (localStorage.getItem("access-token")) {
        // nextPage의 값이 main이다 === 기존 회원
        // 메인 페이지로 돌려보낸다.
        if (nextPage === "main") {
          window.alert("이미 당신은 생선가게 회원😊");
          return navigate("/");
        }

        // 그게 아니다 === 신규회원
        else {
          return navigate("/input-profile");
        }
      } else {
        navigate("/");
      }
    }
    getTokens();
  });
  return;
}

export default LoginCallback;
