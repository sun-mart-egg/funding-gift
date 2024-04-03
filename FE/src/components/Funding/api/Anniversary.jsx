import axios from "axios";

async function getAnniversaryList(token, setData) {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_BASE_URL}/api/anniversary-category`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    console.log("기념일 카테고리 정보 : " + response.data);
    setData(response.data.data);
  } catch (error) {
    console.error("기념일 카테고리 리스트 불러오기 실패", error);
  }
}

export { getAnniversaryList };
