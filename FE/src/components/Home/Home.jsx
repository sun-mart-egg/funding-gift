import { useState, useEffect, useRef, useCallback } from "react";
import SearchBar from "../UI/SearchBar";
import { useNavigate } from "react-router-dom";

import CatIcon from "/imgs/cat.PNG";
import FishIcon from "/imgs/fish.PNG";
import BannerImage1 from "/imgs/banner_image1.png"
import BannerImage2 from "/imgs/banner_image2.png"
import BannerImage3 from "/imgs/banner_image3.png"

import HomeProduct from "./HomeProduct"
import ScrollToTop from "../UI/ScrollToTop";

import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';

// import { initializeApp } from 'firebase/app';
// import { getMessaging, getToken, onMessage } from 'firebase/messaging';

function Home() {

  const observer = useRef();
  const navigate = useNavigate();

  const [currentBanner, setCurrentBanner] = useState(0);
  const bannerImages = [BannerImage1, BannerImage2, BannerImage3];

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  // firebase 연결
  const firebaseConfig = {
    apiKey: 'AIzaSyBE1OaWA2Bo3bxh-8oUfJCKGGFz6DkNYbA',
    authDomain: 'funding-gift.firebaseapp.com',
    projectId: 'funding-gift',
    storageBucket: 'funding-gift.appspot.com',
    messagingSenderId: '184194517827',
    appId: '1:184194517827:web:f2a715c4f6c082503afdf6',
    measurementId: 'G-GPCQJX1FSL',
  };

  const firebaseApp = initializeApp(firebaseConfig);
  const messaging = getMessaging(firebaseApp);

  // 권한 요청
  Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      console.log('Notification permission granted.');
    }
    else {
      console.log('not granted');
    }
  });

  // fcm 알림 받기
  onMessage(messaging, (payload) => {
    console.log('Message received. ', payload);
    alert(payload.notification.body);
    // ...
  });

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

  const loadProducts = async (page) => {
    setLoading(true);
    try {
      const response = await fetch(import.meta.env.VITE_BASE_URL + `/api/products/rank?page=${page}&size=10`);
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

  useEffect(() => {
    loadProducts(currentPage);
  }, [currentPage]);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentBanner((prevIndex) => (prevIndex + 1) % bannerImages.length);
    }, 5000); // Change image every 5000 milliseconds

    return () => clearInterval(interval); // Clean up interval on component unmount
  }, []); // Empty dependency array means this runs once on mount

  return (
    <div className="main-layer font-cusFont2">
      <div className="w-full">
        <SearchBar />
      </div>

      <div className="mt-5 h-[150px] w-[95%] rounded-md relative overflow-hidden">
        {bannerImages.map((image, index) => (
          <img
            key={index}
            src={image}
            alt={`Banner ${index + 1}`}
            className={`absolute top-0 left-0 h-full w-full rounded-lg object-cover transition-opacity duration-1000 ${index === currentBanner ? 'opacity-100' : 'opacity-0'}`}
          />
        ))}
      </div>

      <div className="flex w-[95%]">
        <div
          onClick={() => navigate("/make-funding-main")}
          className="relative mr-[1.5%] mt-[15px] h-[100px] w-[50%] rounded-md bg-cusColor4 p-5 text-center"
        >
          <img src={CatIcon} alt="" className="absolute top-[5%] left-1/2 transform -translate-x-1/2 w-[68px] h-[70.5px]" />
          <p className="text-xs absolute left-0 right-0 bottom-2 w-full">펀딩 만들러 가기</p>
        </div>
        <div
          className="relative ml-[1.5%] mt-[15px] h-[100px] w-[50%] rounded-md bg-cusColor3 p-5 text-center"
        >
          <img src={FishIcon} alt="" className="absolute top-[5%] left-1/2 transform -translate-x-1/2 w-[92px] h-[71px]" />
          <p className="text-xs absolute left-0 right-0 bottom-2 w-full">펀딩 참여하러 가기</p>
        </div>
      </div>

      <div className="mt-[10px] w-[95%] ml-[5%] text-left">
        <p className="font-cusFont5 text-[30px] mt-[10px]">추천 상품</p>
        <div>
          <HomeProduct />
        </div>
      </div>
      <ScrollToTop className="bottom-[25px]" />
    </div>
  );
}

export default Home;
