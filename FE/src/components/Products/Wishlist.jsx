import React, { useState, useEffect, useRef, useCallback } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import ScrollToTop from "../UI/ScrollToTop2";
import ImageComingSoon from '/imgs/image_coming_soon.png'
import NoWishlist from '/imgs/no_wishlist.png'


function Wishlist() {

	const navigate = useNavigate();
	const [loading, setLoading] = useState(true);
	const [currentPage, setCurrentPage] = useState(0);
	const [hasMore, setHasMore] = useState(true);
	const [wishes, setWishes] = useState([]);

	const token = localStorage.getItem("access-token");

	useEffect(() => {

		if (!token) {
			console.log("토큰이 존재하지 않습니다.");
			setLoading(false); // 토큰이 없는 경우 로딩 상태를 해제합니다.
			// 추가적으로 사용자를 로그인 페이지로 리다이렉트할 수 있습니다.
			navigate("/login-page");
			return;
		}
	}, [token, navigate])

	const observer = useRef();

	const lastProductElementRef = useCallback(node => {
		if (loading) return;
		if (observer.current) observer.current.disconnect();
		observer.current = new IntersectionObserver(entries => {
			if (entries[0].isIntersecting && hasMore) {
				setCurrentPage(prevPage => prevPage + 1);
			}
		});
		if (node) observer.current.observe(node);
	}, [loading, hasMore]);

	useEffect(() => {
		// Reset products when categoryId or sort changes
		setCurrentPage(0);
		setWishes([]);
	}, []);

	useEffect(() => {
		// Fetch products when currentPage changes
		loadWishes(currentPage);
	}, [currentPage]);

	const renderNoResultsMessage = () => {
		if (!loading & wishes.length === 0) {
			return (
				<div className='w-full h-full text-center flex flex-col items-center'>
					<p className='text-2xl font-cusFont4 pt-[30px]'>상품 목록에서 위시리스트를 추가해보세요!</p>
					<img src={NoWishlist} className='w-[90%] h-auto'></img>
					<Link to={`/product`}>
						<button className='bg-cusColor3 text-white text-2xl mt-[20px] px-[20px] py-[10px] rounded-md'>
							바로가기
						</button>
					</Link>
				</div>

			)
		}
		return null;
	};

	const loadWishes = async (page) => {
		setLoading(true);
		try {
			const response = await fetch(import.meta.env.VITE_BASE_URL + `/api/wishlists?page=${page}&size=10`,
				{ headers: { Authorization: `Bearer ${token}` } }
			);
			const json = await response.json();
			if (json.code === 200 && json.data) {
				const newData = page === 0 ? json.data.data : [...wishes, ...json.data.data.filter(newItem => !wishes.some(wish => wish.productId === newItem.productId))];
				setWishes(newData);
				setHasMore(json.data.hasNext === true);
			} else {
				console.error('Error fetching wishes:', json.msg);
			}
		} catch (error) {
			console.error('Error fetching wishes:', error);
		}
		setLoading(false);
	};

	const numberWithCommas = (number) => {
		return number.toLocaleString();
	};

	const formatReviewNum = (num) => {
		return num >= 1000 ? "999+" : num;
	};



	return (
		<div className="sub-layer mt-[80px] justify-start min-h-screen overflow-hidden font-cusFont2 bg-white">
			<p className="font-cusFont5 text-4xl">나의 위시리스트</p>
			<div className="mt-4 flex min-h-[63%] w-[95.5%] flex-grow flex-wrap justify-center overflow-y-auto bg-white font-cusfont2">
				{wishes.map((product, index) => (
					<div
						key={product.productId}
						ref={index === wishes.length - 1 ? lastProductElementRef : null}
						className="border-[1px] border-gray-300 m-2 h-auto w-[45%] flex-col rounded-md text-[12px]"
					>
						{/* 이미지 */}
						<div className="w-full relative pt-[100%] rounded-md"> {/* paddingTop is same as width to maintain 1:1 aspect ratio */}
							<Link to={`/product/${product.productId}`}>
								<img src={product.imageUrl || ImageComingSoon} alt="" className="absolute top-0 left-0 w-[100%] h-[100%] object-cover rounded-md" />
							</Link>
						</div>
						{/* 상품 정보 */}
						<div className="m-[1px] flex h-[30%] w-[100%] flex-col justify-center p-[10px] pl-2">
							<div>
								<p className='truncate'>{product.productName}</p>
								<p>{numberWithCommas(product.price)}원</p>
							</div>
						</div>
					</div>
				))}
				{loading && <p>Loading more products...</p>}
				{renderNoResultsMessage()} {/* 검색 결과가 없을 때 메시지 표시 */}
			</div>

			<ScrollToTop />
		</div>
	);
}


export default Wishlist