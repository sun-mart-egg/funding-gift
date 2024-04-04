import axios from "axios";

async function getAddressList(token, setData) {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_BASE_URL}/api/addresses`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    setData(response.data.data);
  } catch (error) {
    console.error("주소 목록 정보를 불러올 수 없습니다. ", error);
  }
}

async function deleteAddress(token, addressId) {
  try {
    const response = await axios.delete(
      `${import.meta.env.VITE_BASE_URL}/api/addresses/${addressId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    console.log(".주소 삭제 응답 : " + response.data);
  } catch (error) {
    console.error("주소를 삭제하는데 실패했습니다. ", error);
  }
}

//주소 만들기 api
async function createAddress(
  addressName,
  defaultAddr,
  detailAddr,
  zipCode,
  isDefault,
  token,
) {
  const requestBody = {
    name: addressName,
    defaultAddr: defaultAddr,
    detailAddr: detailAddr,
    zipCode: zipCode,
    isDefault: isDefault,
  };

  try {
    const response = await fetch(
      import.meta.env.VITE_BASE_URL + "/api/addresses",
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
    console.log("주소 생성 응답:", responseData);
    // 추가적인 후속 처리 작업이 필요하면 여기에서 수행
  } catch (error) {
    console.error("주소 생성 중 오류 발생:", error);
  }
}
export { getAddressList, deleteAddress, createAddress };
