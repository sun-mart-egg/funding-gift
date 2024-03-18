import React, { useState } from "react";

const BottomSheet = ({ title, children, isOpen, setIsOpen }) => {
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
                    <div className="text-center">Swipe here to {isOpen ? "close" : "open"}</div>
                </div>
                <div className="w-full bg-white overflow-y-auto">{children}</div>
            </div>
        </div>
    );
};

export default BottomSheet;
