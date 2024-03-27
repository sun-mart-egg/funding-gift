import React, { useState, useEffect, useRef, useCallback } from 'react';
import { Link } from 'react-router-dom';

import Star from '/imgs/star.png';
import Logo from '/imgs/logo.png';

function ProductComponent({ categoryId, sort }) {
  console.log('ProductComponent 렌더링');

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

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
    setProducts([]);
  }, [categoryId, sort]);

  useEffect(() => {
    // Fetch products when currentPage changes
    loadProducts(currentPage);
  }, [currentPage, categoryId, sort]);

  const loadProducts = async (page) => {
    setLoading(true);
    try {
      const response = await fetch(`https://j10d201.p.ssafy.io/api/products?category-id=${categoryId}&page=${page}&size=10&sort=${sort}`);
      const json = await response.json();
      if (json.code === 200 && json.data) {
        // 현재 페이지가 0인 경우 새 데이터 설정, 그 외에는 기존 데이터에 추가
        const newData = page === 0 ? json.data.data : [...products, ...json.data.data.filter(newItem => !products.some(prevItem => prevItem.productId === newItem.productId))];
        setProducts(newData);
        setHasMore(json.data.hasNext === true);
      } else {
        console.error('Error fetching products:', json.msg);
      }
    } catch (error) {
      console.error('Error fetching products:', error);
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
    <div className="mt-4 flex min-h-[63%] w-[95.5%] flex-grow flex-wrap justify-center overflow-y-auto bg-white font-cusfont2">
      {products.map((product, index) => (
        <div
          key={product.productId}
          ref={index === products.length - 1 ? lastProductElementRef : null}
          className="m-2 h-[58.5%] w-[45%] flex-col rounded-md text-[12px]"
        >
          {/* 이미지 */}
          <div className="h-[70%] w-[100%]">
            <Link to={`/product/${product.productId}`}>
              <img src={Logo} alt="" className="h-full w-full rounded-md border border-gray-300 shadow" />
            </Link>
          </div>
          {/* 상품 정보 */}
          <div className="m-[1px] flex h-[30%] w-[100%] flex-col justify-center p-[10px] pl-2">
            <div>
              <p>{product.productName}</p>
              <p>{numberWithCommas(product.price)}원</p>
              <p className="flex items-center">
                <img src={Star} alt="" className="h-[12px] w-[12px]" />
                <span className="ml-1">
                  {formatReviewNum(product.reviewCnt)}({product.reviewAvg})
                </span>
              </p>
            </div>
          </div>
        </div>
      ))}
      {loading && <p>Loading more products...</p>}
    </div>
  );
}

export default ProductComponent;
