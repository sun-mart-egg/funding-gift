import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction"
import { useState } from "react";

function Calendar() {
  // 이벤트 목록 받아올 배열
  const [selectedEvents, setSelectedEvents] = useState([]);
  const [selectedDay, setSelectedDay] = useState(null)

  // 캘린더 날짜 선택 시
  const handleDateClick = (arg) => {
    const clickedDate = arg.dateStr
    const ThisDate = myEvents.filter((event) => {
      const clickDay = event.date
      return clickedDate === clickDay
    })
    setSelectedEvents(ThisDate)
    setSelectedDay(clickedDate)
  };

  const myEvents = [
    { title: "이벤트1", date: "2024-04-02", name: "신시은" },
    { title: "이벤트1", date: "2024-04-03", name: "신시은" },
    { title: "이벤트1", date: "2024-04-04", name: "신시은" },
    { title: "이벤트1", date: "2024-04-05", name: "신시은" },
    { title: "이벤트1", date: "2024-04-30", name: "신시은" },
  ];

  return (
    <div className="sub-layer">
      <div className="absolute w-full top-[65px]">
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          initialView="dayGridMonth"
          events={myEvents}
          dateClick={handleDateClick}
          locale="kr"
          headerToolbar={
            {
              left: "prev",
              center: "title",
              right: "today next"
            }
          }
        />
      </div>

      <div className="flex flex-col w-full h-[267px] p-3 border-t-2 z-10 bg-white text-xl signup-font gap-2 absolute bottom-0">
        {selectedDay}
        {/* 선택한 날짜에 대한 기념일 목록 출력 */}
        {selectedEvents.length > 0 ? (
          selectedEvents.map((event, index) => (
            <div key={index}>
              <p>😘 {event.name}의 {event.title}</p>
            </div>
          ))
        ) : (
          <div>
            <p>기념일이 없습니다.</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default Calendar;
