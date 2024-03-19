import { BrowserRouter, Routes, Route } from "react-router-dom";

import MyFunding from "./components/Funding/pages/MyFunding";
import MakeFundingMain from "./components/Funding/pages/MakeFundingMain";
import Product from "./components/Products/Product";
import ProductDetail from "./components/Products/ProductDetail";
import BrandStore from "./components/Products/BrandStore";
import Wishlist from "./components/Products/Wishlist";
import Signup from "./components/Login/Signup.jsx";
import MakeFundingDetail from "./components/Funding/pages/MakeFundingDetail.jsx";
import MakeFundingFinish from "./components/Funding/pages/MakeFundingFinish.jsx";
function App() {
    return (
        <>
            <FriendFundingDetail />
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<MakeFundingDetail />} /> {/* 루트 경로 설정 */}
                    <Route path="/my-funding" element={<MyFunding />} />
                    <Route path="/make-funding" element={<MakeFundingMain />} />
                    <Route path="/make-funding-detail" element={<MakeFundingDetail />} />
                    <Route path="/make-funding-finish" element={<MakeFundingFinish />} /> {/* 이 부분 추가 */}
                    <Route path="/product" element={<Product />} />
                    <Route path="/product/:productId" element={<ProductDetail />} />
                    <Route path="/brand/:brandId" element={<BrandStore />} />
                    <Route path="/wishlist/:userId" element={<Wishlist />} />
                    <Route path="/signup" element={<Signup />} />
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
