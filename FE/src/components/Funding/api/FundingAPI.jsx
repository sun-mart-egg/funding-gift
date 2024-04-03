import axios from "axios";

//펀딩 상세 조회 api
async function fetchDetailFunding(token, fundingId, setData) {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_BASE_URL}/api/fundings/detail/${fundingId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    console.log("디테일 정보 : " + response.data);
    setData(response.data.data);
  } catch (error) {
    console.error("펀딩 디테일 정보를 불러올 수 없습니다. ", error);
  }
}

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

const formatDate = (date) => {
  const d = new Date(date);
  let month = "" + (d.getMonth() + 1);
  let day = "" + d.getDate();
  let year = d.getFullYear();

  if (month.length < 2) month = "0" + month;
  if (day.length < 2) day = "0" + day;

  return [year, month, day].join("-");
};

//펀딩 만들기 api
async function createFunding(formData, token) {
  // 날짜를 한국 시간대로 변환하는 함수
  const toKoreanTimeZone = (date) => {
    const userTimezoneOffset = date.getTimezoneOffset() * 60000;
    return new Date(date.getTime() + userTimezoneOffset + 9 * 60 * 60 * 1000);
  };

  // 한국 시간대로 날짜 조정
  const modifiedFormData = {
    ...formData,
    startDate: formatDate(toKoreanTimeZone(formData.startDate)),
    endDate: formatDate(toKoreanTimeZone(formData.endDate)),
  };

  console.log("Modified form data:", JSON.stringify(modifiedFormData));

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

//펀딩 피드 api
async function getFundingFeed(token, setData) {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_BASE_URL}/api/fundings/feed`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          page: 0,
          size: 8,
          sort: "", // sort가 필요하다면 'columnName,asc' 또는 'columnName,desc' 형식의 값을 설정하세요.
        },
      },
    );
    console.log("펀딩 피드 가져오기 : ", response.data.data.data);
    setData(response.data.data.data);
  } catch (error) {
    console.error("펀딩 피드를 불러올 수 없습니다.", error);
  }
}

export {
  createFunding,
  fetchFriendFunding,
  fetchMyFundings,
  fetchDetailFunding,
  getFundingFeed,
};
