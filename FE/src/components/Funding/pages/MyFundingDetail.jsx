import FundingDetailInfo from "../component/FundingDetailInfo";
import CongratulateList from "../component/CongratulateList";
import BottomSheet from "../component/BottomSheet";
import React, { useState } from "react";
function MyFundingDetail() {
    const data = {
        title: "EGG IS MY LIFE",
        name: "계란 토스트",
        price: 760000,
        detail: "친구들아 안녕. 곧 내 생일인데 고오급 계란 토스트가 너무 가지고 싶어. 많은 참여 부탁해",
        progress: 70,
    };

    const friendList = [
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

    const [isBottomSheetOpen, setIsBottomSheetOpen] = useState(false);

    const toggleBottomSheet = () => {
        setIsBottomSheetOpen(!isBottomSheetOpen);
    };

    return (
        <div>
            <div id="page" className="flex flex-col items-center justify-center h-screen overflow-auto">
                <FundingDetailInfo
                    title={data.title}
                    name={data.name}
                    detail={data.detail}
                    progress={data.progress}
                    price={data.price}
                />
                <div id="messageSection" className="flex-col w-full m-2">
                    <div id="subTitle" className="text-left">
                        <p>축하 받은 리스트</p>
                    </div>
                </div>
                <CongratulateList listData={friendList} onCardClick={toggleBottomSheet} />
            </div>
            <BottomSheet isOpen={isBottomSheetOpen} setIsOpen={setIsBottomSheetOpen}>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque tincidunt dui laoreet sapien
                euismod dictum. Phasellus ornare ligula non ante eleifend, id efficitur diam laoreet. Donec dapibus, est
                ut aliquam mollis, ligula purus porta ipsum, sed luctus nunc ex sit amet arcu. Suspendisse molestie arcu
                ac egestas lobortis. Aliquam eu nisi vel risus suscipit venenatis vitae non velit. Aliquam vel tellus
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque tincidunt dui laoreet sapien
                euismod dictum. Phasellus ornare ligula non ante eleifend, id efficitur diam laoreet. Donec dapibus, est
                ut aliquam mollis, ligula purus porta ipsum, sed luctus nunc ex sit amet arcu. Suspendisse molestie arcu
                ac egestas lobortis. Aliquam eu nisi vel risus suscipit venenatis vitae non velit. Aliquam vel tellus
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque tincidunt dui laoreet sapien
                euismod dictum. Phasellus ornare ligula non ante eleifend, id efficitur diam laoreet. Donec dapibus, est
                ut aliquam mollis, ligula purus porta ipsum, sed luctus nunc ex sit amet arcu. Suspendisse molestie arcu
                ac egestas lobortis. Aliquam eu nisi vel risus suscipit venenatis vitae non velit. Aliquam vel tellus
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque tincidunt dui laoreet sapien
                euismod dictum. Phasellus ornare ligula non ante eleifend, id efficitur diam laoreet. Donec dapibus, est
                ut aliquam mollis, ligula purus porta ipsum, sed luctus nunc ex sit amet arcu. Suspendisse molestie arcu
                ac egestas lobortis. Aliquam eu nisi vel risus suscipit venenatis vitae non velit. Aliquam vel tellus
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque tincidunt dui laoreet sapien
                euismod dictum. Phasellus ornare ligula non ante eleifend, id efficitur diam laoreet. Donec dapibus, est
                ut aliquam mollis, ligula purus porta ipsum, sed luctus nunc ex sit amet arcu. Suspendisse molestie arcu
                ac egestas lobortis. Aliquam eu nisi vel risus suscipit venenatis vitae non velit. Aliquam vel tellus
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque tincidunt dui laoreet sapien
                euismod dictum. Phasellus ornare ligula non ante eleifend, id efficitur diam laoreet. Donec dapibus, est
                ut aliquam mollis, ligula purus porta ipsum, sed luctus nunc ex sit amet arcu. Suspendisse molestie arcu
                ac egestas lobortis. Aliquam eu nisi vel risus suscipit venenatis vitae non velit. Aliquam vel tellus
            </BottomSheet>
        </div>
    );
}

export default MyFundingDetail;
