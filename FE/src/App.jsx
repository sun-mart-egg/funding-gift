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
import Login from "./components/Login/Login.jsx"


function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<ParticipatePage />} />
          <Route path="/pay" element={<Paypage />} />
          <Route path="/product" element={<Product />} />
          <Route path="/product/:productId" element={<ProductDetail />} />
          <Route path="/brand/:brandId" element={<BrandStore />} />
          <Route path="/wishlist/:userId" element={<Wishlist />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
