import FundingDetailInfo from "../component/FundingDetailInfo";
import CongratulateList from "../component/CongratulateList";
import BottomSheet from "../component/BottomSheet";
import React, { useState } from "react";

function MyFundingDetail() {
  const data = {
    title: "EGG IS MY LIFE",
    name: "계란 토스트",
    price: 760000,
    detail:
      "친구들아 안녕. 곧 내 생일인데 고오급 계란 토스트가 너무 가지고 싶어. 많은 참여 부탁해",
    progress: 70,
  };

  const MessageList = [
    {
      name: "박창준",
      title: "시은아 생일 축하해",
      detail:
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nunc erat, tincidunt nec efficitur nec, porta vitae ipsum. Nunc cursus pretium sapien non tristique. Donec feugiat nec erat ut ultricies. Mauris eleifend id erat id finibus. Sed suscipit justo nisi, id tincidunt lectus porttitor et. Proin id viverra felis, pulvinar ullamcorper lectus. Quisque lacinia euismod laoreet. Aenean ornare metus varius pellentesque egestas. Vivamus faucibus libero eu ornare pretium. Duis porta laoreet erat id condimentum.Ut ligula felis, suscipit quis velit non, aliquam viverra tellus. Etiam id commodo urna. Morbi dictum quam et turpis rutrum, ac ornare turpis dapibus. Morbi consectetur ex vel ante commodo, sit amet mattis risus gravida. Mauris gravida sit amet libero at commodo. Sed sagittis elit tristique auctor blandit. Quisque purus urna, imperdiet id cursus et, consequat at nisl. Duis egestas augue vitae enim dictum semper. Quisque a efficitur erat. In non purus sit amet nunc rhoncus consectetur eget sed nisl. Sed id ligula dui. Aenean viverra fringilla quam, in scelerisque dolor malesuada et. Donec tortor lorem, vulputate vitae molestie ac, feugiat in lorem. Duis auctor et orci at pellentesque. Donec tempus leo nec dignissim consequatIn id suscipit enim. Nullam fringilla condimentum pretium. In congue interdum eros, ac pellentesque metus consequat in. Vivamus imperdiet, magna id facilisis dictum, orci eros scelerisque quam, vel hendrerit ligula arcu quis massa. Vivamus eget ligula non quam bibendum consectetur vel tincidunt orci. Fusce nibh nisi, tempus sit amet vehicula at, varius at ipsum. Curabitur lacinia euismod urna, sed scelerisque tellus ultrices at. Sed eu pharetra mauris, eget elementum mi. Nulla vestibulum erat id nisi ullamcorper, non consectetur libero tempus. Integer tortor massa, varius quis mi porta, maximus sagittis ante. Quisque interdum eros sit amet justo vestibulum lacinia.Aliquam feugiat massa magna, a efficitur enim rhoncus ac. Vestibulum tristique orci at molestie laoreet. In ullamcorper lectus et nisl fermentum vestibulum. Praesent sagittis sed odio id commodo. Duis nisl libero, maximus lobortis elit luctus, pharetra accumsan ipsum. Donec molestie sem ac massa elementum, ut maximus urna ultrices. Mauris dapibus urna quis erat accumsan, sed imperdiet purus mattis.Donec non nisi fermentum, laoreet dolor eu, placerat lorem. Donec iaculis eleifend mollis. Phasellus vel suscipit eros. Maecenas eget nibh erat. In hac habitasse platea dictumst. In nec arcu in purus finibus rutrum in ut sapien. Vivamus vulputate tellus in tortor vulputate convallis. Nulla facilisi. Pellentesque non eros id libero pellentesque iaculis. Donec nec faucibus erat. Quisque sed libero risus. Maecenas in lorem non urna placerat vehicula ac non nulla. Etiam vitae scelerisque orci. Vivamus pharetra finibus nunc at luctus.",
      reply: "ㄳㄳ",
    },
    {
      name: "임수빈",
      title: "시은아 생일 축하해1",
      detail: "메세지 내용 테스트임 2",
      reply: "ㄳㄳ",
    },
    {
      name: "김대영",
      title: "시은아 생일 축하해2",
      detail: "메세지 내용 테스트임 3",
      reply: null,
    },
    {
      name: "이민수",
      title: "시은아 생일 축하해3",
      detail: "메세지 내용 테스트임 4",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail: "메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail: "메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail: "메세지 내용 테스트임 5",
      reply: null,
    },
  ];

  const [isBottomSheetOpen, setIsBottomSheetOpen, selectId] = useState(false);
  const [selectedMessage, setSelectedMessage] = useState("");

  const toggleBottomSheet = (message) => {
    setSelectedMessage(message);
    setIsBottomSheetOpen(!isBottomSheetOpen);
  };

  return (
    <div className="sub-layer">
      <div
        id="page"
        className="flex h-screen flex-col items-center justify-center "
      >
        <FundingDetailInfo
          title={data.title}
          name={data.name}
          detail={data.detail}
          progress={data.progress}
          price={data.price}
        />
        <div id="messageSection" className="m-2 w-full flex-col">
          <div id="subTitle" className="text-left">
            <p>축하 받은 리스트</p>
          </div>
        </div>
        <CongratulateList
          listData={MessageList}
          onCardClick={toggleBottomSheet}
        />
      </div>
      <BottomSheet
        isOpen={isBottomSheetOpen}
        setIsOpen={setIsBottomSheetOpen}
        message={selectedMessage}
      ></BottomSheet>
    </div>
  );
}

export default MyFundingDetail;
