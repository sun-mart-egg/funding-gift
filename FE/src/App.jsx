import { BrowserRouter, Routes, Route } from "react-router-dom";

import MyFunding from "./components/Funding/pages/MyFunding";
import AccountListPage from "./components/Funding/pages/AccountListPage.jsx";
import MakeFundingMain from "./components/Funding/pages/MakeFundingMain";
import Product from "./components/Products/Product";
import ProductDetail from "./components/Products/ProductDetail";
import BrandStore from "./components/Products/BrandStore";
import Wishlist from "./components/Products/Wishlist";
import AddressListPage from "./components/Funding/pages/AddressListPage.jsx";
import Signup from "./components/Login/Signup.jsx";
import AnniversaryListPage from "./components/Funding/pages/AnniversaryListPage.jsx";
import FriendFundingDetail from "./components/Funding/pages/FriendFundingDetail.jsx";
import ParticipatePage from "./components/Funding/pages/ParticipatePage.jsx";
import Paypage from "./components/Funding/pages/Paypage.jsx";
import MakeFundingDetail from "./components/Funding/pages/MakeFundingDetail.jsx";
import MakeFundingFinish from "./components/Funding/pages/MakeFundingFinish.jsx";
import Login from "./components/Login/Login.jsx";
import MainLayout from "./components/UI/MainLayout.jsx";

function App() {
  return (
    <div className="main-layer">
      <BrowserRouter>
        <Routes>
          <Route path="/make-funding-detail" element={<MakeFundingDetail />} />
          <Route element={<MainLayout />}>
            <Route path="/" element={<MyFunding />} />
            <Route path="/login" element={<Login />} />
            <Route path="/product" element={<Product />} />
          </Route>
          <Route path="/make-funding-detail" element={<MakeFundingDetail />} />
          <Route path="/make-funding-main" element={<MakeFundingMain />} />
          <Route path="/participate" element={<ParticipatePage />} />
          <Route path="/pay" element={<Paypage />} />
          <Route path="/product/:productId" element={<ProductDetail />} />
          <Route path="/brand/:brandId" element={<BrandStore />} />
          <Route path="/wishlist/:userId" element={<Wishlist />} />
          <Route path="/login" element={<Login />} />
          <Route path="/anniversary-list" element={<AnniversaryListPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
