import { BrowserRouter, Routes, Route } from "react-router-dom";

import MainLayout from "./components/UI/MainLayout.jsx";
import SubLayout from "./components/UI/SubLayout.jsx";

import Login from "./components/Login/LoginPage.jsx";
import LoginCallback from "./components/Login/LoginCallback.jsx";
import Signup from "./components/Login/Signup.jsx";
import SignupFinish from "./components/Login/SignupFinish.jsx";
import InputProfile from "./components/Login/InputProfile.jsx";

import Calendar from "./components/Friends/Calendar.jsx";
import Friends from "./components/Friends/Friends.jsx";

import MyFunding from "./components/Funding/pages/MyFunding";
import AccountListPage from "./components/Funding/pages/AccountListPage.jsx";
import MakeFundingMain from "./components/Funding/pages/MakeFundingMain";
import Product from "./components/Products/Product";
import ProductDetail from "./components/Products/ProductDetail";
import BrandStore from "./components/Products/BrandStore";
import Wishlist from "./components/Products/Wishlist";
import AddressListPage from "./components/Funding/pages/AddressListPage.jsx";
import AnniversaryListPage from "./components/Funding/pages/AnniversaryListPage.jsx";
import FriendFundingDetail from "./components/Funding/pages/FriendFundingDetail.jsx";
import ParticipatePage from "./components/Funding/pages/ParticipatePage.jsx";
import Paypage from "./components/Funding/pages/Paypage.jsx";
import MakeFundingDetail from "./components/Funding/pages/MakeFundingDetail.jsx";
import MakeFundingFinish from "./components/Funding/pages/MakeFundingFinish.jsx";
import Home from "./components/Home/Home.jsx";
import MyFundingDetail from "./components/Funding/pages/MyFundingDetail.jsx";
import FundingMain from "./components/Funding/pages/FundingMain.jsx";
import StoryPage from "./components/Funding/pages/StoryPage.jsx";
import MyPage from "./components/Funding/pages/MyPage.jsx";
import NewAnniversaryPage from "./components/Funding/pages/NewAnniversaryPage.jsx";
import NewAddressPage from "./components/Funding/pages/NewAddressPage.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/" element={<Home />} />
          <Route path="/my-funding" element={<MyFunding />} />
          <Route path="/product" element={<Product />} />
          <Route path="/funding" element={<FundingMain />} />
        </Route>

        <Route element={<SubLayout />}>
          <Route path="/login-page" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/input-profile" element={<InputProfile />} />
          <Route path="/signupFin" element={<SignupFinish />} />
          <Route path="/make-funding-main" element={<MakeFundingMain />} />
          <Route path="/my-funding-detail" element={<MyFundingDetail />} />
          <Route
            path="/friend-funding-detail"
            element={<FriendFundingDetail />}
          />
          <Route path="/my-page" element={<MyPage />} />

          <Route path="/make-funding-detail" element={<MakeFundingDetail />} />
          <Route path="/make-funding-finish" element={<MakeFundingFinish />} />
          <Route path="/product/:productId" element={<ProductDetail />} />
          <Route path="/anniversary-list" element={<AnniversaryListPage />} />
          <Route path="/address-list" element={<AddressListPage />} />
          <Route path="/account-list" element={<AccountListPage />} />
          <Route path="/new-anniversary" element={<NewAnniversaryPage />} />
          <Route path="/new-address" element={<NewAddressPage />} />
          <Route path="/calendar" element={<Calendar />} />
          <Route path="/friends" element={<Friends />} />
          <Route path="/participate" element={<ParticipatePage />} />
          <Route path="/pay" element={<Paypage />} />
          <Route path="/wishlist" element={<Wishlist />} />
          <Route path="/brand/:brandId" element={<BrandStore />} />
        </Route>

        <Route path="/login-callback" element={<LoginCallback />} />
        <Route path="/story/:id" element={<StoryPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
