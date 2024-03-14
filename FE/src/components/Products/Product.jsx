import React, { useState } from "react";

function Product() {

  const [selectedButtonId, setSelectedButtonId] = useState(1)

  // const handleButtonClick = (id) => {
  // 	setSelectedButtonId(id);
  // };

  const buttons = [
    { id: 1, text: '전체' },
    { id: 2, text: '패션' },
    { id: 3, text: '뷰티' },
    { id: 4, text: '식품' },
    { id: 5, text: '스포츠' },
    { id: 6, text: '가전' },
    { id: 7, text: '건강' },
  ];

  const products = [
    { id: 1, name: '상품 1' },
    { id: 2, name: '상품 2' },
    { id: 3, name: '상품 3' },
    { id: 4, name: '상품 4' },
    { id: 5, name: '상품 5' },
    { id: 6, name: '상품 6' },
    { id: 7, name: '상품 7' },
    { id: 8, name: '상품 8' },
    { id: 9, name: '상품 9' },
    { id: 10, name: '상품 10' },
    { id: 11, name: '상품 11' },
    { id: 12, name: '상품 12' },
  ];

  return (

    // 배경 임시로 회색으로 설정 -> 나중에 이미지 혹은 단일 색으로 채울 것(웹 기준)
    <div className="flex items-center bg-gray-100 justify-center min-h-screen">

      {/* 화면 - 패딩은 모바일 기준이므로 요소들이 너무 구석에 가지 않도록 설정 */}
      <div className="w-[400px] h-[600px] min-h-[600px] p-5 bg-white flex flex-col items-center justify-start">

        {/* 버튼 영역 */}
        <div className="flex justify-center w-[92.5%] h-[10%]">
          {/* 카테고리 버튼이 7개 있으므로, 내용만 바꿔서 7번 반복할 것*/}
          {buttons.map(button => (
            <div key={button.id} className="flex flex-col items-center w-[100%] h-[100%]">
              <button
                className="bg-cusColor3 w-[70%] h-[100%]"
                onClick={() => setSelectedButtonId(button.id)}
              >
                {/* 여기에 버튼 이미지 또는 아이콘 */}
              </button>
              <span className="mt-2 text-[12px]">{button.text}</span>
            </div>
          ))}
        </div>

        {/* 검색 창 */}
        <div className="mt-[3%] w-[89.0%] h-[6%] bg-cusColor3">

        </div>

        {/* 물품 수, 정렬순서 토글, 필터 */}
        <div className="flex items-center justify-between w-[87.5%] h-[6%] mt-2">
          <div>
            <span className="text-[12px]">물품 수: 12345</span>
          </div>
          <div className="flex items-center">
            <div className="text-[12px]">
              토글
            </div>
            <div className="bg-cusColor5 text-white text-[12px]">
              필터
            </div>
          </div>
        </div>

        {/* 물품 목록(무한 스크롤) */}
        <div className="flex flex-wrap flex-grow overflow-y-auto justify-center mt-4 w-[95.5%] bg-white">
          {products.map(product => (
            <div key={product.id} className="w-[45%] h-[35%] p-2 m-1 bg-gray-200 text-[12px]">
              {product.name}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Product;
