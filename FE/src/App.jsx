import { BrowserRouter, Routes, Route } from "react-router-dom";

import MyFunding from "./components/Funding/pages/MyFunding";
import MakeFundingMain from "./components/Funding/pages/MakeFundingMain";
import Product from "./components/Products/Product";
import ProductDetail from "./components/Products/ProductDetail";
import BrandStore from "./components/Products/BrandStore";
import Wishlist from "./components/Products/Wishlist";
import Signup from "./components/Login/Signup.jsx";
import FriendFundingDetail from "./components/Funding/pages/FriendFundingDetail.jsx";

function App() {
    return (
        <>
            <FriendFundingDetail />
            <BrowserRouter>
                <Routes>
                    <Route path="/product" element={<Product />} />
                    <Route path="/product/:productId" element={<ProductDetail />} />
                    <Route path="/brand/:brandId" element={<BrandStore />} />
                    <Route path="/wishlist/:userId" element={<Wishlist />} />
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
