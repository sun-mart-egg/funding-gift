import axios from "axios";

async function createAttendance(
  token,
  fundingId,
  sendMessage,
  sendMessageTitle,
  price,
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
    console.log("펀딩 참여 생성 응답:", responseData);
  } catch (error) {
    console.error("펀딩 참여 생성 중 오류 발생:", error);
  }
}

export { createAttendance };
