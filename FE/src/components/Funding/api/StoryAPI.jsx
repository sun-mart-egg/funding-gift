import axios from "axios";

async function getStoryList(token) {
  try {
    const response = await axios.get(
      import.meta.env.VITE_BASE_URL + "/api/friends/fundings-story",

      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    console.log(response.data.data);
    return response.data.data;
  } catch (error) {
    console.error("진행중인 펀딩 목록 불러오기 실패:", error);
    throw error; // 호출한 쪽에서 에러를 처리할 수 있도록 에러를 던집니다.
  }
}

async function getStory(token, id) {
  try {
    const response = await axios.get(
      "https://j10d201.p.ssafy.io/api/fundings/story", // 직접적인 URL 사용
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          "consumer-id": id, // 동적으로 id 값을 할당
        },
      },
    );
    console.log(response.data.data);
    return response.data.data;
  } catch (error) {
    console.error("진행중인 펀딩 목록 불러오기 실패:", error);
    throw error;
  }
}
export { getStoryList, getStory };
