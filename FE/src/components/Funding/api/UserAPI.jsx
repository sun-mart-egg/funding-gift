// services/friendService.js

import axios from "axios";

async function getFriends(token) {
  try {
    const response = await axios.get(
      import.meta.env.VITE_BASE_URL + "/api/friends",

      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    return response.data;
  } catch (error) {
    console.error("친구 목록 불러오기 실패:", error);
    throw error; // 호출한 쪽에서 에러를 처리할 수 있도록 에러를 던집니다.
  }
}

async function fetchUserInfo() {
  try {
    const response = await axios.get(
      import.meta.env.VITE_BASE_URL + "/api/consumers",
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("access-token")}`,
        },
      },
    );

    // 응답 데이터 콘솔에 출력
    console.log("Received user info:", response.data.data.profileImageUrl);

    // API 응답으로부터 받은 데이터로 userInfo 상태 업데이트
    setUserInfo({
      ...userInfo,
      name: response.data.data.name,
      img: response.data.data.profileImageUrl,
    });
  } catch (error) {
    console.error("Failed to fetch user info:", error);
  }
}

export { getFriends, fetchUserInfo };
