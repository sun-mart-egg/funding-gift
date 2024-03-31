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
async function fetchFriendFunding(friend, token, setData) {
  if (friend.profileNickname !== "Unknown") {
    try {
      const params = new URLSearchParams({
        "friend-consumer-id": friend.consumerId,
        page: "0",
        size: "8",
        sort: "createdAt",
        sort: "DESC",
      });

      const response = await fetch(
        `${import.meta.env.VITE_BASE_URL}/api/fundings/friend-fundings?${params.toString()}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const responseData = await response.json();
      console.log(
        "친구 펀딩 목록 조회 : " + JSON.stringify(responseData, null, 2),
      );
      setData((prevData) => [
        ...prevData,
        ...(Array.isArray(responseData) ? responseData : [responseData]),
      ]);
    } catch (error) {
      console.error("친구가 만든 펀딩을 불러오는데 실패했습니다.", error);
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
