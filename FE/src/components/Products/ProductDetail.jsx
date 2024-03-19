import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

function ProductDetail() {
  const { productId } = useParams();
  const [product, setProduct] = useState(null);

  useEffect(() => {
    // 상품 데이터 로드 함수
    const fetchProductData = async () => {
      try {
        // 예시: 여기서는 하드코딩된 API URL을 사용합니다.
        // 실제 애플리케이션에서는 이 부분을 적절한 API 호출로 대체해야 합니다.
        const response = await fetch(`https://example.com/api/products/${productId}`);
        const data = await response.json();
        setProduct(data);
      } catch (error) {
        console.error("Error fetching product data:", error);
      }
    };

    fetchProductData();
  }, [productId]);

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>{product.name}</h1>
      <p>{product.description}</p>
      {/* 기타 상품에 대한 상세 정보 표시 */}
    </div>
  );
}

export default ProductDetail;