import axios from "axios";
//내 펀딩 조회 api

async function fetchMyFundings(token, setMyFundings, setIsLoading) {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_BASE_URL}/api/fundings/my-fundings`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          page: 0,
          size: 8,
        },
      },
    );
    setMyFundings(response.data.data.data);
  } catch (error) {
    console.error("내가 만든 펀딩을 불러오는데 실패했습니다.", error);
  } finally {
    setIsLoading(false);
  }
}

//친구 펀딩 조회 api
async function fetchFriendFunding(friendId, token, setData) {
  if (friendId !== "Unknown") {
    try {
      const params = new URLSearchParams({
        "friend-consumer-id": friendId,
        page: "0",
        size: "8",
        sort: "createdAt",
        sort: "DESC", // 정렬 파라미터 수정
      });
      const response = await axios.get(
        `${import.meta.env.VITE_BASE_URL}/api/fundings/friend-fundings?${params.toString()}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      setData(response.data.data.data);
    } catch (error) {
      console.error("친구 펀딩을 불러오는데 실패했습니다.", error);
    }
  }
}

//펀딩 만들기 api
async function createFunding(formData, token) {
  const currentDateTime = new Date().toISOString();
  const modifiedFormData = {
    ...formData,
    startDate: formData.startDate.toISOString(),
    endDate: formData.endDate.toISOString(),
    anniversaryDate: currentDateTime,
  };

  console.log(JSON.stringify(modifiedFormData));
  const response = await fetch(
    import.meta.env.VITE_BASE_URL + "/api/fundings",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(modifiedFormData),
    },
  );

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  return await response.json();
}
export { createFunding, fetchFriendFunding, fetchMyFundings };
