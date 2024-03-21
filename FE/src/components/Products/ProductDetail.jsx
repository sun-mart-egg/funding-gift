import React, { useState } from "react";

import SampleImage from '../../../public/imgs/product_sample.png'

import Star from '../../../public/imgs/star.png'
import Heart from '../../../public/imgs/heart.png'
import Down from '../../../public/imgs/down.png'
import SampleReview from '../../../public/imgs/samplereview.jpg'
import ActivatedSmile from '../../../public/imgs/smile_activated.png'
import DeactivatedSmile from '../../../public/imgs/smile_deactivated.png'

function ProductDetail() {

	const [toggleListVisible, setToggleListVisible] = useState(false);
	const [selectedFilter, setSelectedFilter] = useState('최신 순');
	const [smileStates, setSmileStates] = useState({});

	const toggleListVisibility = () => {
		setToggleListVisible(!toggleListVisible);
	};

	const handleFilterChange = (filter) => {
		setSelectedFilter(filter);
		setToggleListVisible(false); // 필터를 선택하면 목록을 닫음
	};

	const handleSmileClick = (id) => {
		setSmileStates(prevState => ({
			...prevState,
			[id]: !prevState[id] // 해당 리뷰의 상태를 토글
		}));
	};

	const reviews = [
		{
			id: 1,
			profileImage: '../../../public/imgs/egg3.jpg',
			rating: 1,
			name: '이민수',
			images: [SampleReview],
			content: '선물을 받았는데 벽돌이 왔어요 ㅠㅠ',
			helpfulCount: 12,
		},
		{
			id: 2,
			profileImage: '../../../public/imgs/basic.jpg',
			rating: 5,
			name: '홍보2',
			images: [],
			content: '너무 좋아요. 받았는데 최신 제품이 왔어요. 모두 꼭 구입하세요. 이 글을 복사 후 붙여넣기 해주세요.(이 문장은 지우고 올려주세요)',
			helpfulCount: 1,
		},
		{
			id: 3,
			profileImage: '../../../public/imgs/basic.jpg',
			rating: 5,
			name: '홍보1',
			images: [],
			content: '너무 좋아요. 받았는데 최신 제품이 왔어요. 모두 꼭 구입하세요. 이 글을 복사 후 붙여넣기 해주세요.(이 문장은 지우고 올려주세요)',
			helpfulCount: 1,
		},
	];

	return (
		<>
			{/* 배경 임시로 회색으로 설정 -> 나중에 이미지 혹은 단일 색으로 채울 것(웹 기준) */}
			<div className="flex items-center bg-gray-100 overflow-y-auto justify-center h-screen font-cusFont2 overflow-x-hidden">

				{/* 화면 - 패딩은 모바일 기준이므로 요소들이 너무 구석에 가지 않도록 설정 */}
				<div className="flex-start overflow-y-auto h-screen max-w-[500px] min-h-[600px] bg-white flex flex-col items-center justify-start overflow-x-hidden">

					{/* 상단바 자리 (브랜드 버튼) */}
					<div></div>

					{/* 본문 자리 */}
					<div className="mt-[70px]">

						{/* 이미지 들어갈 영역 */}
						<div className="w-[100%] h-[400px] flex justify-center border-b-[2px] border-b-cusColor3">
							<img src={SampleImage} alt="" className="max-w-full max-h-full" />
						</div>

						{/* 이미지 하단 영역 */}
						<div className="p-[15px]">

							{/* 상품 정보 */}
							<div>

								{/* 상품 명 */}
								<p className='mb-[5px] text-lg text-bold'>상품 명이 들어갈 자리입니다.</p>

								{/* 별점 정보 */}
								<div className='flex mb-[5px]'>
									<img src={Star} alt="" className="w-[4%] aspect-auto object-contain" />
									<span className="ml-1 text-base-bold">3.7 (3)</span>
								</div>

								{/* 가격 정보 */}
								<div className="text-base-bold">760,000원</div>

							</div>

							<br />

							{/*후기 수, 정렬순서 토글, 필터 */}
							<div className="flex items-center justify-between w-[100%] h-[50px] mt-2">
								<div>
									<span className="text-base">선물 후기 (3)</span>
								</div>
								<div className="relative w-[40%] text-right">
									<button className="text-base rounded-md flex w-[100%]  text-right items-center justify-end" onClick={toggleListVisibility}>
										{selectedFilter}
										<img src={Down} alt="" className="h-[14px] w-[10%] ml-[5px] invert" />
									</button>
									{toggleListVisible && (
										<div className="absolute w-[120%] top-full right-0 bg-white p-2 px-[3%] text-center border border-gray-300 rounded-md text-xs">
											<p className={`${selectedFilter === '최신 순' ? 'bg-cusColor3 text-white' : ''} mb-1`} onClick={() => handleFilterChange('최신 순')}>최신 순</p>
											<p className={`${selectedFilter === '추천 순' ? 'bg-cusColor3 text-white' : ''} mb-1`} onClick={() => handleFilterChange('추천 순')}>추천 순</p>
											<p className={`${selectedFilter === '별점 높은 순' ? 'bg-cusColor3 text-white' : ''} mb-1`} onClick={() => handleFilterChange('별점 높은 순')}>별점 높은 순</p>
											<p className={`${selectedFilter === '별점 낮은 순' ? 'bg-cusColor3 text-white' : ''}`} onClick={() => handleFilterChange('별점 낮은 순')}>별점 낮은 순</p>
										</div>
									)}
								</div>
							</div>

							{/* 리뷰 리스트 */}
							<div className="mt-[20px]">
								{reviews.map((review) => (
									<div key={review.id} className="flex flex-col justify-between items-start w-full mt-4">

										{/* 리뷰 프로필 이미지, 별점, 이름 */}
										<div className="flex items-center mb-2">
											<div className="w-[18%] aspect-square rounded-full overflow-hidden bg-black mr-[3%]">
												<img
													src={review.profileImage}
													alt={review.name}
													className="w-full h-full object-cover rounded-full"
												/>
											</div>
											<div className="w-[100%]">
												<div className="flex items-center">
													<img src={Star} alt="" className="w-[7%] aspect-auto object-contain pb-1" />
													<span className="ml-1">{review.rating} / 5</span>
												</div>
												<div>{review.name}</div>
											</div>
										</div>

										{/* 리뷰 이미지 */}
										{review.images.length > 0 && (
											<div className="flex w-full my-[20px]">
												{review.images.map((image, index) => (
													<div key={index} className={`w-[45%] mx-[2.5%] h-[150px] bg-gray-300 rounded-md`}>
														<img src={image} alt={`Review Image ${index + 1}`} className="w-full h-full object-cover rounded-md" />
													</div>
												))}
											</div>
										)}


										{/* 리뷰 내용 */}
										<div className="w-[100%] h-[100px] ml-7-1 border-[2px] p-2 rounded-md mb-2">
											<p className="text-xs">{review.content}</p>
										</div>

										{/* 도움돼요 버튼 */}
										<div className="ml-auto w-full flex justify-end text-xs">
											<button
												className={`w-[30%] h-[40px] border-[3px] rounded-md flex items-center justify-center ${smileStates[review.id] ? 'border-cusColor3 text-cusColor3' : ''}`}
												onClick={() => handleSmileClick(review.id)}
											>
												<img className="mt-[8px] w-[25%] mr-1" src={smileStates[review.id] ? ActivatedSmile : DeactivatedSmile} alt="Smile" />
												<p className={`${smileStates[review.id] ? 'text-cusColor3' : 'text-gray-400'} text-xs`}>도움돼요 {review.helpfulCount}</p>
											</button>
										</div>
									</div>
								))}
							</div>
						</div>
					</div>
					<div className="min-h-[70px]">
					</div>
					{/* 하단 고정 버튼 */}
					<div className="fixed bottom-0 w-[97%] max-w-[500px] h-[50px] bg-white flex justify-center items-center">
						<button className="absolute left-[8%] mr-[20px]">
							<img src={Heart} alt="" className="w-[27px]" />
						</button>
						<button className="bg-cusColor3  w-[50%] h-[80%] hover:bg-red-400 text-white text-base font-bold py-2 px-4 rounded-lg">
							펀딩 만들기
						</button>
					</div>
				</div>
			</div>
		</>
	);
}

export default ProductDetail;

