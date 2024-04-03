import axios from "axios";

//펀딩 참여하기 api
async function createAttendance(
  token,
  fundingId,
  sendMessage,
  sendMessageTitle,
  price,
  setData,
) {
  const requestBody = {
    sendMessageTitle: sendMessageTitle,
    sendMessage: sendMessage,
    price: price,
    fundingId: fundingId,
  };

  try {
    const response = await fetch(
      import.meta.env.VITE_BASE_URL + "/api/attendance",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(requestBody),
      },
    );

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const responseData = await response.json();

    setData(responseData);
    console.log("펀딩 참여 생성 응답:", responseData);
  } catch (error) {
    console.error("펀딩 참여 생성 중 오류 발생:", error);
  }
}

//내가 참여한 펀딩 api
async function getMyAttendance(token, setData) {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_BASE_URL}/api/fundings/my-attendance-fundings`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    console.log("내가 참여한 펀딩 정보 응답 : " + response.data.data.data);
    setData(response.data.data.data);
  } catch (error) {
    console.error("내가 참여한 펀딩 정보 불러오기 에러", error);
  }
}

export { createAttendance, getMyAttendance };
