import FundingDetailInfo from "../component/FundingDetailInfo";
import CongratulateList from "../component/CongratulateList";
import BottomSheet from "../component/BottomSheet";
import React, { useState } from "react";
function FriendFundingDetail() {
    const data = {
        frinedName: "신시은",
        title: "EGG IS MY LIFE",
        name: "계란 토스트",
        price: 760000,
        detail: "친구들아 안녕. 곧 내 생일인데 고오급 계란 토스트가 너무 가지고 싶어. 많은 참여 부탁해",
        progress: 70,
    };

    const myParticipate = {
        date: "2024.3.19.12:00",
        price: 100000,
        title: "시은아 생일 축하해",
        detail: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nunc erat, tincidunt nec efficitur nec, porta vitae ipsum. Nunc cursus pretium sapien non tristique. Donec feugiat nec erat ut ultricies. Mauris eleifend id erat id finibus. Sed suscipit justo nisi, id tincidunt lectus porttitor et. Proin id viverra felis, pulvinar ullamcorper lectus. Quisque lacinia euismod laoreet. Aenean ornare metus varius pellentesque egestas. Vivamus faucibus libero eu ornare pretium. Duis porta laoreet erat id condimentum.Ut ligula felis, suscipit quis velit non, aliquam viverra tellus. Etiam id commodo urna. Morbi dictum quam et turpis rutrum, ac ornare turpis dapibus. Morbi consectetur ex vel ante commodo, sit amet mattis risus gravida. Mauris gravida sit amet libero at commodo. Sed sagittis elit tristique auctor blandit. Quisque purus urna, imperdiet id cursus et, consequat at nisl. Duis egestas augue vitae enim dictum semper. Quisque a efficitur erat. In non purus sit amet nunc rhoncus consectetur eget sed nisl. Sed id ligula dui. Aenean viverra fringilla quam, in scelerisque dolor malesuada et. Donec tortor lorem, vulputate vitae molestie ac, feugiat in lorem. Duis auctor et orci at pellentesque. Donec tempus leo nec dignissim consequatIn id suscipit enim. Nullam fringilla condimentum pretium. In congue interdum eros, ac pellentesque metus consequat in. Vivamus imperdiet, magna id facilisis dictum, orci eros scelerisque quam, vel hendrerit ligula arcu quis massa. Vivamus eget ligula non quam bibendum consectetur vel tincidunt orci. Fusce nibh nisi, tempus sit amet vehicula at, varius at ipsum. Curabitur lacinia euismod urna, sed scelerisque tellus ultrices at. Sed eu pharetra mauris, eget elementum mi. Nulla vestibulum erat id nisi ullamcorper, non consectetur libero tempus. Integer tortor massa, varius quis mi porta, maximus sagittis ante. Quisque interdum eros sit amet justo vestibulum lacinia.Aliquam feugiat massa magna, a efficitur enim rhoncus ac. Vestibulum tristique orci at molestie laoreet. In ullamcorper lectus et nisl fermentum vestibulum. Praesent sagittis sed odio id commodo. Duis nisl libero, maximus lobortis elit luctus, pharetra accumsan ipsum. Donec molestie sem ac massa elementum, ut maximus urna ultrices. Mauris dapibus urna quis erat accumsan, sed imperdiet purus mattis.Donec non nisi fermentum, laoreet dolor eu, placerat lorem. Donec iaculis eleifend mollis. Phasellus vel suscipit eros. Maecenas eget nibh erat. In hac habitasse platea dictumst. In nec arcu in purus finibus rutrum in ut sapien. Vivamus vulputate tellus in tortor vulputate convallis. Nulla facilisi. Pellentesque non eros id libero pellentesque iaculis. Donec nec faucibus erat. Quisque sed libero risus. Maecenas in lorem non urna placerat vehicula ac non nulla. Etiam vitae scelerisque orci. Vivamus pharetra finibus nunc at luctus.",
        reply: "ㄳㄳ",
    };

    const MessageList = [
        {
            name: "박창준",
            title: "시은아 생일 축하해",
        },
        {
            name: "임수빈",
            title: "시은아 생일 축하해1",
        },
        {
            name: "김대영",
            title: "시은아 생일 축하해2",
        },
        {
            name: "이민수",
            title: "시은아 생일 축하해3",
        },
        {
            name: "박종혁",
            title: "시은아 생일 축하해4",
        },
    ];

    const [isBottomSheetOpen, setIsBottomSheetOpen, selectId] = useState(false);
    const [selectedMessage, setSelectedMessage] = useState("");

    const toggleBottomSheet = () => {
        setSelectedMessage(myParticipate);
        setIsBottomSheetOpen(!isBottomSheetOpen);
    };

    return (
        <div className="relative min-h-screen">
            <div id="page" className="flex flex-col items-center justify-center overflow-auto pb-16">
                <FundingDetailInfo
                    friendName={data.frinedName}
                    title={data.title}
                    name={data.name}
                    detail={data.detail}
                    progress={data.progress}
                    price={data.price}
                />
                <div id="participateSection" className="flex-col w-full m-2">
                    <div id="subTitle" className="text-left">
                        <p>내가 참여한 펀딩</p>
                    </div>
                </div>
                <div>
                    {myParticipate.price === null ? (
                        <div>
                            <p>아직 펀딩에 참여하지 않았네요</p>
                            <p>펀딩에 참여하여 친구를 축하해 보세요!</p>
                        </div>
                    ) : (
                        <div className="flex-col justify-center items-center border border-black m-2 cursor-pointer">
                            <p>{data.frinedName}님의 펀딩에 참여했어요!</p>
                            <p>{myParticipate.date}</p>
                            <p>{myParticipate.price}원</p>
                            <button className="p-2 bg-blue-500 text-white rounded" onClick={toggleBottomSheet}>
                                자세히보기
                            </button>
                        </div>
                    )}
                </div>
                <div id="messageSection" className="flex-col w-full m-2">
                    <div id="subTitle" className="text-left">
                        <p>펀딩에 참여한 사람</p>
                    </div>
                </div>
                <CongratulateList listData={MessageList} />
            </div>
            {myParticipate.price == null ? (
                <button className="fixed bottom-0 left-28 bg-blue-500 text-white py-3">펀딩 참여하기</button>
            ) : (
                <button className="fixed bottom-0 left-28 bg-blue-500 text-white py-3">펀딩 추가 참여하기 </button>
            )}

            <BottomSheet
                isOpen={isBottomSheetOpen}
                setIsOpen={setIsBottomSheetOpen}
                message={selectedMessage}
            ></BottomSheet>
        </div>
    );
}

export default FriendFundingDetail;
