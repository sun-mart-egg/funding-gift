import React, { useEffect, useState } from "react";
import { getAttendanceDetail } from "../api/AttendanceAPI";

const BottomSheet = ({
  message,
  fundingId,
  children,
  isOpen,
  setIsOpen,
  updateReply,
  attendanceDetail,
}) => {
  const [startTouchY, setStartTouchY] = useState(0);
  const [currentTouchY, setCurrentTouchY] = useState(0);
  const [isEditing, setIsEditing] = useState(false);
  const [editReply, setEditReply] = useState("");

  const handleTouchStart = (e) => {
    setStartTouchY(e.touches[0].clientY);
  };

  const handleTouchMove = (e) => {
    setCurrentTouchY(e.touches[0].clientY);
  };

  const handleTouchEnd = () => {
    if (startTouchY - currentTouchY > 50) {
      setIsOpen(true);
    } else if (startTouchY - currentTouchY < -50) {
      setIsOpen(false);
    }
  };

  const handleEdit = () => {
    setIsEditing(true);
    setEditReply(message.reply);
  };

  const handleSave = () => {
    updateReply(message.name, editReply);
    setIsEditing(false);
    setEditReply(""); // 상태 초기화
  };

  const handleDelete = () => {
    updateReply(message.name, null);
    setIsEditing(false);
    setEditReply(""); // 상태 초기화
  };

  if (!attendanceDetail) return <div></div>;

  return (
    <div
      className={`fixed inset-0 flex transform items-end bg-gray-900 bg-opacity-50 transition-transform ${isOpen ? "translate-y-0" : "translate-y-full"} z-50`}
      style={{ maxHeight: "100vh" }}
    >
      <div className="flex max-h-96 w-full flex-col justify-center font-cusFont4">
        <div
          className="flex justify-center rounded-t-lg bg-[#E5EEFF] p-5"
          onTouchStart={handleTouchStart}
          onTouchMove={handleTouchMove}
          onTouchEnd={handleTouchEnd}
        >
          <div className="h-4 w-1/2 rounded-lg bg-[#D9D9D9]"></div>
        </div>
        <div
          id="messageBack"
          className="relative flex w-full flex-col items-start overflow-y-auto bg-[#E5EEFF]"
        >
          <div
            id="message"
            className="relative mx-auto mt-4 flex h-2/5 w-[85%] flex-col space-y-2 rounded-lg bg-white p-4"
          >
            <div className="flex h-full flex-col items-center justify-center font-cusFont4">
              <p className="pt-2 text-[30px]">{message.sendMessageTitle}</p>
              <p className="pt-2 text-[18px]">by {message.consumerName}</p>
              <p className="pt-3 text-[18px]">{attendanceDetail.sendMessage}</p>
            </div>
            <img
              src="/imgs/redRibbon.png"
              className="absolute right-[-20px] top-[-20px] rounded-full"
              alt="Description"
            />
          </div>

          {isEditing ? (
            <div className="mx-auto w-[85%] p-4">
              <textarea
                className="w-full p-2"
                value={editReply}
                onChange={(e) => setEditReply(e.target.value)}
              />
              <button
                className="mt-2 w-full rounded bg-cusColor3 p-2 text-white"
                onClick={handleSave}
              >
                답장 저장
              </button>
              <button
                className="mt-2 w-full rounded bg-gray-300 p-2"
                onClick={() => setIsEditing(false)}
              >
                취소
              </button>
            </div>
          ) : (
            <div className="mx-auto w-[93%] p-4">
              {message.reply ? (
                <>
                  <div className="flex h-full flex-col items-center justify-center font-cusFont4">
                    <p className="font mb-[30px] text-[30px]">
                      {message.consumerName} 님에게 보낸 답장
                    </p>{" "}
                    <img
                      src="/imgs/yellowRibbon.png"
                      className="absolute left-0 z-[300] mb-[10px] rounded-full" // 이미지 위치와 크기 조정
                      alt="Description"
                    />
                    <p className="relative mb-[10px] mt-[-20px] flex h-2/5 w-full flex-col space-y-2 rounded-lg bg-white p-4">
                      {message.reply}
                    </p>
                  </div>
                  <button
                    className="mt-2 w-full rounded bg-cusColor3 p-2 text-white"
                    onClick={handleEdit}
                  >
                    답장 수정하기
                  </button>
                  <button
                    className="mt-2 w-full rounded bg-red-500 p-2 text-white"
                    onClick={handleDelete}
                  >
                    답장 삭제하기
                  </button>
                </>
              ) : (
                <button
                  className="mt-2 w-full rounded bg-cusColor3 p-2 text-white"
                  onClick={() => setIsEditing(true)}
                >
                  답장 추가하기
                </button>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default BottomSheet;
