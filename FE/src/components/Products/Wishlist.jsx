import React, { useState } from "react";

import Categories1 from "../../../public/imgs/product_categories1.png";
import Categories2 from "../../../public/imgs/product_categories2.png";
import Categories3 from "../../../public/imgs/product_categories3.png";
import Categories4 from "../../../public/imgs/product_categories4.png";
import Categories5 from "../../../public/imgs/product_categories5.png";
import Categories6 from "../../../public/imgs/product_categories6.png";
import Categories7 from "../../../public/imgs/product_categories7.png";

import ProductSample from "../../../public/imgs/product_sample.png";
import Star from "../../../public/imgs/star.png";
import Down from "../../../public/imgs/down.png";

import ScrollToTop from "../UI/ScrollToTop2";

function Wishlist() {

	const buttons = [
		{ id: 1, text: "전체", image: Categories1 },
		{ id: 2, text: "패션", image: Categories2 },
		{ id: 3, text: "뷰티", image: Categories3 },
		{ id: 4, text: "식품", image: Categories4 },
		{ id: 5, text: "스포츠", image: Categories5 },
		{ id: 6, text: "가전", image: Categories6 },
		{ id: 7, text: "건강", image: Categories7 },
	];

	return (
		<div className="sub-layer mt-[80px] justify-start min-h-screen overflow-hidden font-cusFont2 bg-white">

			<div className="ml-[13%] text-left w-full">
				<p className="text-4xl font-cusFont5 mb-[30px]">나의 위시리스트</p>
			</div>

			<div className="ml-[1%] flex h-[10%] w-[90.5%] justify-center">
				{/* 카테고리 버튼들 */}
				{buttons.map((button) => (
					<div key={button.id} className="flex h-full w-full flex-col items-center">
						<button
							className={`flex h-[100%] w-[80%] items-center justify-center rounded-md 
                ${selectedButtonId === button.id && "bg-cusColor3"}`}
							onClick={() => handleCategorySelection(button.id)}
						>
							<img
								src={button.image}
								alt={button.text}
								className={`h-[90%] w-[90%]
                  ${selectedButtonId === button.id && "invert"}`}
							/>
						</button>
						<span className="mt-2 text-xs">{button.text}</span>
					</div>
				))}
			</div>

			<div className="my-[20px] flex w-[95.5%] flex-grow flex-wrap justify-center bg-white">
				{/* 상품 박스들 */}
				{[1, 2, 3, 4].map((id) => (
					<div key={id} className="m-2 h-[58.5%] w-[45%] flex-col rounded-md border border-gray-300 text-[12px]">
						<div className="image-area h-[70%] w-[100%] bg-gray-200 rounded-md">
							{/* 빈 이미지 대신 사용될 배경 */}
						</div>
						<div className="info-area m-[1px] flex h-[30%] w-[100%] flex-col justify-center p-[10px] pl-2">
							<p className="name-placeholder bg-gray-200 rounded">상품 이름</p>
							<p className="price-placeholder bg-gray-200 rounded">가격 정보</p>
							<p className="review-placeholder flex items-center">
								<span className="bg-gray-200 rounded w-[12px] h-[12px]"></span>
								<span className="ml-1 bg-gray-200 rounded">리뷰 정보</span>
							</p>
						</div>
					</div>
				))}
			</div>
			<ScrollToTop />
		</div>
	);
}


export default Wishlist