import React, { useState } from "react";

import Header from '../UI/Header'
import Footer from '../UI/Footer';

import Categories1 from '../../../public/imgs/product_categories1.png'
import Categories2 from '../../../public/imgs/product_categories2.png'
import Categories3 from '../../../public/imgs/product_categories3.png'
import Categories4 from '../../../public/imgs/product_categories4.png'
import Categories5 from '../../../public/imgs/product_categories5.png'
import Categories6 from '../../../public/imgs/product_categories6.png'
import Categories7 from '../../../public/imgs/product_categories7.png'

import ProductSample from '../../../public/imgs/product_sample.png'

import SearchButton from '../../../public/imgs/heart.png'
import Star from '../../../public/imgs/star.png'
import Down from '../../../public/imgs/down.png'



function Product() {

  const buttons = [
    { id: 1, text: '전체', image: Categories1 },
    { id: 2, text: '패션', image: Categories2 },
    { id: 3, text: '뷰티', image: Categories3 },
    { id: 4, text: '식품', image: Categories4 },
    { id: 5, text: '스포츠', image: Categories5 },
    { id: 6, text: '가전', image: Categories6 },
    { id: 7, text: '건강', image: Categories7 },
  ];

  const products = [
    { id: 1, name: '상품 1', categoryId: 1, price: 1000000, reviewNum: 1000, reviewAvg: 4.9, image: ProductSample },
    { id: 2, name: '상품 2', categoryId: 2, price: 120000, reviewNum: 156, reviewAvg: 5.0, image: ProductSample },
    { id: 3, name: '상품 3', categoryId: 3, price: 200000, reviewNum: 110, reviewAvg: 5.0, image: ProductSample },
    { id: 4, name: '상품 4', categoryId: 4, price: 300000, reviewNum: 910, reviewAvg: 5.0, image: ProductSample },
    { id: 5, name: '상품 5', categoryId: 5, price: 45000, reviewNum: 1400, reviewAvg: 4.8, image: ProductSample },
    { id: 6, name: '상품 6', categoryId: 6, price: 11000000, reviewNum: 400, reviewAvg: 5.0, image: ProductSample },
    { id: 7, name: '상품 7', categoryId: 6, price: 42500, reviewNum: 300, reviewAvg: 5.0, image: ProductSample },
    { id: 8, name: '상품 8', categoryId: 5, price: 70000, reviewNum: 1500, reviewAvg: 5.0, image: ProductSample },
    { id: 9, name: '상품 9', categoryId: 4, price: 104000, reviewNum: 6700, reviewAvg: 4.7, image: ProductSample },
    { id: 10, name: '상품 10', categoryId: 3, price: 13400, reviewNum: 124, reviewAvg: 5.0, image: ProductSample },
    { id: 11, name: '상품 11', categoryId: 2, price: 31000, reviewNum: 1000, reviewAvg: 5.0, image: ProductSample },
    { id: 12, name: '상품 12', categoryId: 1, price: 10000, reviewNum: 1000, reviewAvg: 5.0, image: ProductSample },
  ];

  const [selectedButtonId, setSelectedButtonId] = useState(1);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredProducts, setFilteredProducts] = useState(products);

  const numberWithCommas = (number) => {
    return number.toLocaleString();
  };

  const formatReviewNum = (num) => {
    return num >= 1000 ? "999+" : num;
  };

  // 카테고리 선택 시 필터링
  const handleCategorySelection = (id) => {
    setSelectedButtonId(id);
    setSearchTerm(''); // 검색 상태 초기화
    filterProducts(id, '');
  };

  // 검색 실행
  const handleSearchSubmit = (event) => {
    event.preventDefault();
    filterProducts(selectedButtonId, searchTerm);
  };

  // 상품 필터링 로직
  const filterProducts = (categoryId, search) => {
    const filtered = products.filter(product => {
      const inCategory = categoryId === 1 || product.categoryId === categoryId - 1;
      const inSearch = !search || product.name.toLowerCase().includes(search.toLowerCase());
      return inCategory && inSearch;
    });
    setFilteredProducts(filtered);
  };

  // 토글 핸들러
  const handleToggle = (type) => {
    sortProducts(type);
  };

  // 상품 정렬 로직
  const sortProducts = (type) => {
    let sortedProducts = [...filteredProducts];
    switch (type) {
      case 'default':
        // 기본 정렬(상품 ID 순)
        sortedProducts.sort((a, b) => a.id - b.id);
        break;
      case 'review':
        // 리뷰 순 정렬
        sortedProducts.sort((a, b) => b.reviewNum - a.reviewNum);
        break;
      case 'rating':
        // 평점 순 정렬
        sortedProducts.sort((a, b) => b.reviewAvg - a.reviewAvg);
        break;
      case 'priceHigh':
        // 가격 높은 순 정렬
        sortedProducts.sort((a, b) => b.price - a.price);
        break;
      case 'priceLow':
        // 가격 낮은 순 정렬
        sortedProducts.sort((a, b) => a.price - b.price);
        break;
      default:
        break;
    }
    setFilteredProducts(sortedProducts);
  };

  // 토글 목록 보이기/숨기기 상태를 관리하는 state 변수 추가
  const [toggleListVisible, setToggleListVisible] = useState(false);

  // 선택된 옵션을 관리하는 state 변수 추가
  const [selectedToggle, setSelectedToggle] = useState('기본 순');

  // 토글 목록 보이기/숨기기 함수
  const toggleListVisibility = () => {
    setToggleListVisible(!toggleListVisible);
  };

  // 토글 선택 함수
  const selectToggle = (toggle) => {
    let toggleType;
    switch (toggle) {
      case '기본 순':
        toggleType = 'default';
        break;
      case '리뷰 순':
        toggleType = 'review';
        break;
      case '평점 순':
        toggleType = 'rating';
        break;
      case '가격 높은 순':
        toggleType = 'priceHigh';
        break;
      case '가격 낮은 순':
        toggleType = 'priceLow';
        break;
      default:
        toggleType = 'default'; // 기본값 설정
        break;
    }
    setSelectedToggle(toggle);
    handleToggle(toggleType);
  };
  return (
    <>

      {/* 배경 임시로 회색으로 설정 -> 나중에 이미지 혹은 단일 색으로 채울 것(웹 기준) */}
      <div className="flex items-center bg-gray-100 justify-center min-h-screen font-cusFont2">

        {/* 화면 - 패딩은 모바일 기준이므로 요소들이 너무 구석에 가지 않도록 설정 */}
        <div className="flex-start w-[400px] h-[600px] min-h-[600px] p-2 bg-white flex flex-col items-center justify-start">
          <Header />
          {/* 버튼 영역 */}
          <div className="flex justify-center ml-[1%] w-[90.5%] h-[10%]">
            {/* 카테고리 버튼이 7개 있으므로, 내용만 바꿔서 7번 반복할 것*/}
            {buttons.map(button => (
              <div key={button.id} className="flex flex-col items-center w-full h-full">
                <button
                  className={`flex justify-center items-center w-[80%] h-[100%] rounded-md 
                  ${selectedButtonId === button.id && 'bg-cusColor3'}`}
                  onClick={() => handleCategorySelection(button.id)}
                >
                  <img src={button.image}
                    alt={button.text}
                    className={`w-[90%] h-[90%]
                    ${selectedButtonId === button.id && 'invert'}`
                    } />
                </button>
                <span className="mt-2 text-xs">{button.text}</span>
              </div>
            ))}
          </div>

          {/* 검색 창 */}
          <div className="mt-[5%] w-[89.0%] h-[5.5%] border-[1px] rounded-md border-cusColor3 flex items-center p-2">
            <form onSubmit={handleSearchSubmit} className="flex w-full">
              <input
                type="text"
                placeholder="검색"
                className="flex-grow border-none focus:outline-none text-[12px]"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
              <button type="submit" className="ml-2">
                <img src={SearchButton} alt="검색" className="w-6 h-6" />
              </button>
            </form>
          </div>

          {/* 물품 수, 정렬순서 토글, 필터 */}
          <div className="flex items-center justify-between w-[85.5%] h-[6%] mt-2">
            <div>
              <span className="text-[12px]">물품 수: {products.length}</span>
            </div>
            <div className="mt-2 w-[28%]">
              <button className="text-[12px] justify-center border px-2 py-1 w-[100%] rounded-md bg-cusColor3 text-white flex items-center" onClick={toggleListVisibility}>
                {selectedToggle}
                <img src={Down} alt="" className="h-[12px] w-[9px] ml-[5px]" />
              </button>
              {/* 토글 목록 */}
              {toggleListVisible && (
                <div className="absolute flex-row bg-white p-2 px-[15px] mt-2 text-center border border-gray-300 rounded-md text-[12px]">
                  <button
                    className="block my-1 w-full text-right"
                    onClick={() => selectToggle('기본 순')}
                  >
                    <span className={`${selectedToggle === '기본 순' ? 'bg-cusColor3 text-white' : ''} p-1 rounded`}>
                      기본 순
                    </span>
                  </button>
                  <button
                    className="block my-1 w-full text-right"
                    onClick={() => selectToggle('리뷰 순')}
                  >
                    <span className={`${selectedToggle === '리뷰 순' ? 'bg-cusColor3 text-white' : ''} p-1 rounded`}>
                      리뷰 순
                    </span>
                  </button>
                  <button
                    className="block my-1 w-full text-right"
                    onClick={() => selectToggle('평점 순')}
                  >
                    <span className={`${selectedToggle === '평점 순' ? 'bg-cusColor3 text-white' : ''} p-1 rounded`}>
                      평점 순
                    </span>
                  </button>
                  <button
                    className="block my-1 w-full text-right"
                    onClick={() => selectToggle('가격 높은 순')}
                  >
                    <span className={`${selectedToggle === '가격 높은 순' ? 'bg-cusColor3 text-white' : ''} p-1 rounded`}>
                      가격 높은 순
                    </span>
                  </button>
                  <button
                    className="block my-1 w-full text-right"
                    onClick={() => selectToggle('가격 낮은 순')}
                  >
                    <span className={`${selectedToggle === '가격 낮은 순' ? 'bg-cusColor3 text-white' : ''} p-1 rounded`}>
                      가격 낮은 순
                    </span>
                  </button>
                </div>
              )}
            </div>
          </div>

          {/* 물품 목록(무한 스크롤) */}
          <div className="flex flex-wrap flex-grow overflow-y-auto justify-center mt-4 w-[95.5%] min-h-[63%] bg-white">
            {filteredProducts.map(product => (
              <div key={product.id} className="flex-col w-[45%] h-[58.5%] text-[12px] rounded-md m-2">
                {/* 이미지 영역 */}
                <div className="w-[100%] h-[70%]">
                  <img src={product.image} alt="" className="w-full h-full border rounded-md border-gray-300 shadow" />
                </div>
                {/* Area2 */}
                <div className="w-[100%] h-[30%] flex flex-col justify-center pl-2 p-[10px] m-[1px]">
                  {/* 상품 정보 영역 */}
                  <div>
                    <p>
                      {product.name}
                    </p>
                    <p>
                      {numberWithCommas(product.price)}원
                    </p>
                    <p className="flex items-center">
                      <img src={Star} alt="" className="w-[12px] h-[12px]" />
                      <span className="ml-1">{formatReviewNum(product.reviewNum)}({product.reviewAvg})</span>
                    </p>
                  </div>
                  {/* 추가적인 상품 정보를 나타내는 요소들을 이곳에 추가할 수 있습니다. */}
                </div>
              </div>
            ))}

          </div>
          <Footer />
        </div>
      </div>

    </>

  );
}

export default Product;
