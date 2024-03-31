import React, { useState, useEffect, useRef, useCallback } from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import SearchBar from '../UI/SearchBar'

import Categories1 from "/imgs/product_categories1.png";

import Down from "/imgs/down.png";

import ProductComponent from "./ProductComponent.jsx";
import ScrollToTopButton from "../UI/ScrollToTop.jsx";

function Product() {
	const [buttons, setButtons] = useState([]);
	const [categories, setCategories] = useState([]);

	const [keyword, setKeyword] = useState(''); // 상태 및 업데이트 함수 정의
	const [products, setProducts] = useState([]);
	const [loading, setLoading] = useState(false);
	const [currentPage, setCurrentPage] = useState(0);
	const [hasMore, setHasMore] = useState(true);


	const handleSubmitSearch = () => {
	};

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
		const loadNextPage = async () => {

			await loadProducts(currentPage);

		};

		loadNextPage();
	}, [currentPage]);

	useEffect(() => {
		fetchCategories();
	}, []);

	const fetchCategories = async () => {
		try {
			const response = await fetch('https://j10d201.p.ssafy.io/api/products/categories');
			const json = await response.json();
			if (json.code === 200) {
				const fetchedButtons = [
					{ id: 1, text: "전체", image: Categories1 }
				];

				json.data.forEach((category, index) => {
					fetchedButtons.push({
						id: index + 2, // "전체"가 첫 번째이므로 인덱스에 2를 더함
						text: category.categoryName,
						image: category.categoryImage
					});
				});

				setButtons(fetchedButtons);
			}
		} catch (error) {
			console.error('Error fetching categories:', error);
		}
	};




	const loadProducts = async (page) => {
		setLoading(true);
		try {
			const response = await fetch(`https://j10d201.p.ssafy.io/api/products?category-id=1&page=${page}&size=10&sort=0`);
			const json = await response.json();
			if (json.code === 200 && json.data) {
				// 중복된 데이터 필터링하여 새 데이터 추가
				setProducts(prevProducts => {
					const newData = json.data.data.filter(newItem => !prevProducts.some(prevItem => prevItem.productId === newItem.productId));
					return [...prevProducts, ...newData];
				});
				setHasMore(json.data.hasNext === true);
			} else {
				console.error('Error fetching products:', json.msg);
			}
		} catch (error) {
			console.error('Error fetching products:', error);
		}
		setLoading(false);
	};

	const [searchTerm, setSearchTerm] = useState("");
	const [filteredProducts, setFilteredProducts] = useState(products);

	const numberWithCommas = (number) => {
		return number.toLocaleString();
	};

	const formatReviewNum = (num) => {
		return num >= 1000 ? "999+" : num;
	};

	const [selectedButtonId, setSelectedButtonId] = useState(1);

	// categoryId와 sort 상태 추가
	const [categoryId, setCategoryId] = useState(' '); // '전체'는 빈 문자열로 처리
	const [sort, setSort] = useState('0'); // 기본 순은 '0'

	const handleCategorySelection = (id) => {
		setSelectedButtonId(id);
		// Reset currentPage to 0 when category changes
		setCurrentPage(0);
		// Update categoryId
		const categoryIndex = id - 1;
		const categoryMap = [' ', '1', '2', '3', '4', '5', '6'];
		setCategoryId(categoryMap[categoryIndex]);
	};
	useEffect(() => {
	}, [categoryId]);

	// 토글 목록 보이기/숨기기 상태를 관리하는 state 변수 추가
	const [toggleListVisible, setToggleListVisible] = useState(false);

	const handleToggle = (selectedToggle) => {
		setSelectedToggle(selectedToggle);
		// 정렬 기준 업데이트
		const sortMap = { "기본 순": "0", "리뷰 순": "1", "평점 순": "2", "가격 높은 순": "3", "가격 낮은 순": "4" };
		setSort(sortMap[selectedToggle]);
		setToggleListVisible(false);
	};

	useEffect(() => {
	}, [sort]);

	// 선택된 옵션을 관리하는 state 변수 추가
	const [selectedToggle, setSelectedToggle] = useState("기본 순");

	// 토글 목록 보이기/숨기기 함수
	const toggleListVisibility = () => {
		setToggleListVisible(!toggleListVisible);
	};

	// 토글 선택 함수
	const selectToggle = (toggle) => {
		let toggleType;
		switch (toggle) {
			case "기본 순":
				toggleType = "기본 순";
				break;
			case "리뷰 순":
				toggleType = "리뷰 순";
				break;
			case "평점 순":
				toggleType = "평점 순";
				break;
			case "가격 높은 순":
				toggleType = "가격 높은 순";
				break;
			case "가격 낮은 순":
				toggleType = "가격 낮은 순";
				break;
			default:
				toggleType = "기본 순"; // 기본값 설정
				break;
		}
		setSelectedToggle(toggle);
		handleToggle(toggleType);
	};

	return (
		<>
			<div className="main-layer font-cusFont2">
				{/* 검색 창 */}
				<div className="w-[94.5%] mb-[20px]">
					<SearchBar
						setKeyword={setKeyword}
						onSubmit={handleSubmitSearch}// New prop
					/>
				</div>

				{/* 버튼 영역 */}
				<div className="flex justify-center w-[90.5%] space-x-1">
					{buttons.map((button) => (
						<div
							key={button.id}
							className="flex flex-1 flex-col items-center" // flex-1로 각 항목이 유연하게 크기 조정
						>
							<button
								className={`h-full w-full p-1 flex rounded-md items-center justify-center 
                ${selectedButtonId === button.id ? "bg-cusColor3" : ""}`}
								onClick={() => handleCategorySelection(button.id)}
							>
								<img
									src={button.image}
									alt={button.text}
									className={`h-[90%] w-[90%] 
                    ${selectedButtonId === button.id ? "invert" : ""}`}
								/>
							</button>
							<span className="mt-2 text-xs">{button.text}</span>
						</div>
					))}
				</div>

				{/* 물품 수, 정렬순서 토글, 필터 */}
				<div className="mt-2 flex h-[6%] w-[85.5%] items-center justify-between">
					<div>
						<span className="text-[12px]"></span>
					</div>
					<div className="mt-2 w-[30%]">
						<button
							className="flex w-[100%] items-center justify-center rounded-md border bg-cusColor3 px-2 py-1 text-[12px] text-white"
							onClick={toggleListVisibility}
						>
							{selectedToggle}
							<img src={Down} alt=""
								className="ml-[5px] h-[12px] w-[9px]" />
						</button>
						{/* 토글 목록 */}
						{toggleListVisible && (
							<div className="absolute right-0 z-50 mt-2 w-[40%] flex-row rounded-md border border-gray-300 bg-white p-2 px-[15px] text-center text-[12px]">
								<button
									className="my-1 block w-full text-center"
									onClick={() => selectToggle("기본 순")}
								>
									<span
										className={`${selectedToggle === "기본 순" ? "bg-cusColor3 text-white" : ""} rounded p-1`}
									>
										기본 순
									</span>
								</button>
								<button
									className="my-1 block w-full text-center"
									onClick={() => selectToggle("리뷰 순")}
								>
									<span
										className={`${selectedToggle === "리뷰 순" ? "bg-cusColor3 text-white" : ""} rounded p-1`}
									>
										리뷰 순
									</span>
								</button>
								<button
									className="my-1 block w-full text-center"
									onClick={() => selectToggle("평점 순")}
								>
									<span
										className={`${selectedToggle === "평점 순" ? "bg-cusColor3 text-white" : ""} rounded p-1`}
									>
										평점 순
									</span>
								</button>
								<button
									className="my-1 block w-full text-center"
									onClick={() => selectToggle("가격 높은 순")}
								>
									<span
										className={`${selectedToggle === "가격 높은 순" ? "bg-cusColor3 text-white" : ""} rounded p-1`}
									>
										가격 높은 순
									</span>
								</button>
								<button
									className="my-1 block w-full text-center"
									onClick={() => selectToggle("가격 낮은 순")}
								>
									<span
										className={`${selectedToggle === "가격 낮은 순" ? "bg-cusColor3 text-white" : ""} rounded p-1`}
									>
										가격 낮은 순
									</span>
								</button>
							</div>
						)}
					</div>
				</div>

				{/* 물품 목록 */}
				<ProductComponent categoryId={categoryId} keyword={keyword} sort={sort} />


				<ScrollToTopButton />
			</div>
		</>
	);
}

export default Product;
