import React, { useState } from "react";

const BottomSheet = ({ message, children, isOpen, setIsOpen }) => {
  const [startTouchY, setStartTouchY] = useState(0);
  const [currentTouchY, setCurrentTouchY] = useState(0);

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

  return (
    <div
      className={`fixed inset-0 flex transform items-end bg-gray-900 bg-opacity-50 transition-transform ${
        isOpen ? "translate-y-0" : "translate-y-full"
      } z-50`}
      style={{ maxHeight: "100vh" }}
    >
      <div className="flex max-h-96 w-full flex-col  justify-center font-cusFont4">
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
            className="relative mx-auto mb-20 mt-4 flex h-2/5 w-[85%] flex-col space-y-2 rounded-lg bg-white p-4"
          >
            <div className="flex h-full flex-col items-center justify-center font-cusFont4">
              <p className=" pt-2 text-[30px]">{message.title}</p>
              <p className=" pt-2 text-[18px]">2024. 4. 22 by {message.name}</p>
              <p className=" pt-3 text-[18px]">{message.detail}</p>
            </div>
            <img
              src="imgs/redRibbon.png"
              className="absolute right-[-20px] top-[-20px]  rounded-full " // 이미지 크기와 모양 조정
              alt="Description"
            />
          </div>

          {message.reply === null ? (
            <button className="fixed bottom-4 left-[7.5%] m-auto   w-[85%] rounded bg-cusColor3 p-2 font-cusFont3 text-white">
              {message.name}님에게 답장하기
            </button>
          ) : (
            <div
              id="reply"
              className="relative mx-auto mb-20 mt-[-40px] flex h-2/5 w-[85%] flex-col space-y-2 rounded-lg bg-white p-4"
            >
              <img
                src="imgs/yellowRibbon.png"
                className="absolute left-[-20px] top-[-10px]  rounded-full" // 이미지 위치와 크기 조정
                alt="Description"
              />
              <div className="flex h-full flex-col items-center justify-center font-cusFont4">
                <p className="font text-[30px]">
                  {message.name} 님에게 보낸 답장
                </p>{" "}
                <p>{message.reply}</p>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default BottomSheet;
