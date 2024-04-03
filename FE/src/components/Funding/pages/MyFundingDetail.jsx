import FundingDetailInfo from "../component/FundingDetailInfo";
import CongratulateList from "../component/CongratulateList";
import BottomSheet from "../component/BottomSheet";
import React, { useEffect, useState } from "react";
import { fetchDetailFunding } from "../api/FundingAPI";
import { useParams } from "react-router-dom";
import { deleteFunding } from "../api/FundingAPI";
import { useNavigate } from "react-router-dom";
import { getFundingAttendee } from "../api/AttendanceAPI";
import { getAttendanceDetail } from "../api/AttendanceAPI";

function MyFundingDetail() {
  const navigate = useNavigate();
  const MessageList = [
    {
      name: "박창준",
      title: "시은아 생일 축하해",
      detail:
        "이 편지는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고 지금은 당신에게로 옮겨진 이 편지는 4일 안에 당신 곁을 떠나야 합니다. 이 편지를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다. 복사를 해도 좋습니다. 혹 미신이라 하실지 모르지만 사실입니다.영국에서 HGXWCH이라는 사람은 1930년에 이 편지를 받았습니다. 그는 비서에게 복사해서 보내라고 했습니다. 며칠 뒤에 복권이 당첨되어 20억을 받았습니다. 어떤 이는 이 편지를 받았으나 96시간 이내 자신의 손에서 떠나야 한다는 사실을 잊었습니다. 그는 곧 사직되었습니다. 나중에야 이 사실을 알고 7통의 편지를 보냈는데 다시 좋은 직장을 얻었습니다. 미국의 케네디 대통령은 이 편지를 받았지만 그냥 버렸습니다. 결국 9일 후 그는 암살당했습니다. 기억해 주세요. 이 편지를 보내면 7년의 행운이 있을 것이고 그렇지 않으면 3년의 불행이 있을 것입니다. 그리고 이 편지를 버리거나 낙서를 해서는 절대로 안됩니다. 7통입니다. 이 편지를 받은 사람은 행운이 깃들것입니다. 힘들겠지만 좋은게 좋다고 생각하세요. 7년의 행운을 빌면서....",
      reply: "ㄳㄳ",
    },
    {
      name: "임수빈",
      title: "시은아 생일 축하해1",
      detail: "메세지 내용 테스트임 2",
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
      detail:
        " 메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail:
        " 메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail:
        " 메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail:
        " 메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail:
        " 메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail:
        " 메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5",
      reply: null,
    },
    {
      name: "박종혁",
      title: "시은아 생일 축하해4",
      detail:
        " 메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5메세지 내용 테스트임 5",
      reply: null,
    },
  ];

  const { fundingId } = useParams(); // URL 파라미터에서 fundingId를 가져옵니다.
  const [fundingDetail, setFundingDetail] = useState(null);
  const [isBottomSheetOpen, setIsBottomSheetOpen, selectId] = useState(false);
  const [selectedMessage, setSelectedMessage] = useState("");
  const [messageList, setMessageList] = useState(MessageList);
  const [attendeeList, setAttendeeList] = useState([]);
  const [attendanceDetail, setAttendanceDetail] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("access-token");
    if (token && fundingId) {
      fetchDetailFunding(token, fundingId, setFundingDetail);
      getFundingAttendee(token, fundingId, setAttendeeList);
    }
  }, [fundingId]);

  useEffect(() => {
    if (fundingDetail) {
      console.log("Funding Detail Loaded: ", fundingDetail);
    }
    if (attendeeList) {
      console.log("참여 정보 목록 불러오기 완료", attendeeList);
    }
  }, [fundingDetail, attendeeList]);

  const toggleBottomSheet = (message) => {
    if (!isBottomSheetOpen) {
      setSelectedMessage(message);
      getAttendanceDetail(
        localStorage.getItem("access-token"),
        fundingId,
        message.attendanceId,
        setAttendanceDetail,
      );
    }
    setIsBottomSheetOpen(!isBottomSheetOpen);
  };

  if (!fundingDetail) return <div>Loading...</div>;

  const updateReply = (name, newReply) => {
    const newList = messageList.map((msg) =>
      msg.name === name ? { ...msg, reply: newReply } : msg,
    );
    setMessageList(newList);

    // selectedMessage 상태도 업데이트
    if (selectedMessage && selectedMessage.name === name) {
      setSelectedMessage({ ...selectedMessage, reply: newReply });
    }
  };

  const handleDelete = async () => {
    const token = localStorage.getItem("access-token");
    if (token && fundingId) {
      deleteFunding(token, fundingId, navigate);
    }
  };
  return (
    <div className="sub-layer font-cusFont3">
      <div
        id="page"
        className="absolute top-20 flex flex-col items-center justify-start pb-20"
      >
        <FundingDetailInfo
          title={fundingDetail.title}
          name={fundingDetail.productName}
          detail={fundingDetail.content}
          progress={(fundingDetail.sumPrice / fundingDetail.targetPrice) * 100}
          price={fundingDetail.targetPrice}
          img={fundingDetail.productImage}
          startDate={fundingDetail.startDate}
          endDate={fundingDetail.endDate}
          fundingStatus={fundingDetail.fundingStatus}
        />

        <CongratulateList
          listData={attendeeList}
          onCardClick={toggleBottomSheet}
        />
      </div>

      <BottomSheet
        fundingId={fundingId}
        isOpen={isBottomSheetOpen}
        setIsOpen={setIsBottomSheetOpen}
        message={selectedMessage}
        updateReply={updateReply}
        attendanceDetail={attendanceDetail} // attendanceDetail을 prop으로 추가
      ></BottomSheet>

      <button
        onClick={handleDelete}
        className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white"
      >
        펀딩 취소하기
      </button>
    </div>
  );
}

export default MyFundingDetail;
