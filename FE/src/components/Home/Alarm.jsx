import React from "react";

import Megaphone from "/imgs/alarm_megaphone.png"
import Gift from "/imgs/alarm_gift.png"
import Friend from "/imgs/alarm_friend.png"

function Alarm() {

  const dummyAlarm = [
    {
      type: "system",
      context: "공지!! 공지!!",
      read: false
    },
    {
      type: "friend",
      context: "친구!! 친구!!",
      read: false
    },
    {
      type: "funding",
      context: "펀딩!! 펀딩!!",
      read: true
    }
  ]

  const getImage = (type) => {
    switch (type) {
      case 'system':
        return Megaphone;
      case 'friend':
        return Friend;
      case 'funding':
        return Gift;
      default:
        return null;
    }
  };

  return (
    <div className="relative flex h-screen max-w-[500px] flex-col items-center justify-start bg-white pb-20 mt-[80px]">
      <p className="font-cusFont5 text-4xl mb-[20px]">알람</p>

      {/* 알람 */}
      <div className="alarms-container flex flex-col justify-start">
        {dummyAlarm.map((alarm, index) => (
          <div key={index} className="alarm-item flex flex-col w-full h-full mb-[10px]">
            <div className="flex w-full items-center">
              <div className="w-[20%] px-[5%] border-r-[3px]">
                <img src={getImage(alarm.type)} alt={alarm.type} className="w-full" />
              </div>
              <p className="font-cusFont2 m-4 flex-grow">{alarm.context}</p>
              {!alarm.read && <div className="h-2.5 w-2.5 bg-red-500 rounded-full ml-auto mr-[30px]"></div>} {/* 빨간 점 추가 */}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}


export default Alarm;
