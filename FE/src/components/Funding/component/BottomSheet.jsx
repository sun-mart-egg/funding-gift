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
            className={`fixed inset-0 flex items-end bg-gray-900 bg-opacity-50 transition-transform transform ${
                isOpen ? "translate-y-0" : "translate-y-full"
            } z-50`}
        >
            <div className="w-full flex flex-col max-h-96">
                <div
                    className="bg-white rounded-t-lg p-5"
                    onTouchStart={handleTouchStart}
                    onTouchMove={handleTouchMove}
                    onTouchEnd={handleTouchEnd}
                >
                    <div className="bg-gray-400 w-1/2 rounded-lg h-4"></div>
                </div>
                <div className="w-full bg-white overflow-y-auto">
                    <div>{message.title}</div>
                    <div>by {message.name}</div>
                    <div>{message.detail}</div>
                    {message.reply === null ? (
                        <button className="p-2 bg-blue-500 text-white rounded">{message.name}님에게 답장하기</button>
                    ) : (
                        <div> {message.reply}</div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default BottomSheet;
