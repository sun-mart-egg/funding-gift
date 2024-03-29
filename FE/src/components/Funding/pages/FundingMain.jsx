import React from "react";
import StoryList from "../component/StoryList";
import FundingList from "../component/FundingList";
import ScrollToTopButton from "../../UI/ScrollToTop";

function FundingMain() {
  let myData = {
    people: "신시은",
    img: "/imgs/egg3.jpg",
  };

  let data = [
    {
      id: 0,
      people: "박창준",
      title: "GAME IS MY LIFE",
      name: "닌텐도 스위치",
      date: "2024.4.15 ~ 2024.4.22",
      img: "/imgs/egg3.jpg",
      progress: 10,
    },

    {
      id: 1,
      people: "임수빈",
      title: "HEALTH IS MY LIFE",
      name: "단백질 세트",
      date: "2024.4.15 ~ 2024.4.22",
      img: "/imgs/egg3.jpg",
      progress: 70,
    },
    {
      id: 1,
      people: "임수빈",
      title: "FOOD IS MY LIFE",
      name: "냠냠 세트",
      date: "2024.4.15 ~ 2024.4.22",
      img: "/imgs/egg3.jpg",
      progress: 70,
    },

    {
      id: 3,
      people: "김대영",
      title: "MUSIC IS MY LIFE",
      name: "에어팟 맥스",
      date: "2024.4.15 ~ 2024.4.22",
      img: "/imgs/egg3.jpg",
      progress: 100,
    },
    {
      id: 4,
      people: "박종혁",
      title: "FOOD IS MY LIFE",
      name: "고오급 케이크",
      date: "2024.4.15 ~ 2024.4.22",
      progress: 20,
      img: "/imgs/product_sample.png",
    },
    {
      id: 5,
      people: "이민수",
      title: "FOOD IS MY LIFE",
      name: "고오급 케이크",
      date: "2024.4.15 ~ 2024.4.22",
      img: "/imgs/egg3.jpg",
      progress: 20,
    },
  ];

  return (
    <div className="sub-layer relative">
      <div className="story absolute top-14 flex border-b border-gray-400 font-cusFont3 text-[12px]">
        <div className="flex-none flex-col items-center justify-center p-4">
          <img
            src={myData.img}
            alt={myData.people}
            className="h-14 w-14 rounded-full" // 너비와 높이를 24로 설정
          />
          <p className="text-center">{myData.people}</p>
        </div>
        <StoryList listData={data} />
      </div>

      <div className="main absolute top-44 w-full  pb-24">
        <FundingList listData={data} />
      </div>
      <ScrollToTopButton className="bottom-[25px]" />
    </div>
  );
}

export default FundingMain;
