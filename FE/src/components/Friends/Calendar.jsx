import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useState } from "react";

function Calendar() {
  const [selectedEvents, setSelectedEvents] = useState([]);
  const handleDateClick = (arg) => {
    const clickedDate = arg.dateStr;
    const ThisDate = myEvents.filter((event) => {
      const startDay = event.start;
      const endDay = event.end;
      return clickedDate >= startDay && clickedDate <= endDay;
    });
    setSelectedEvents(ThisDate);
  };

  const myEvents = [
    { title: "이벤트1", start: "2024-04-01", end: "2024-04-03" },
    { title: "이벤트1", start: "2024-04-01", end: "2024-04-03" },
    { title: "이벤트1", start: "2024-04-01", end: "2024-04-03" },
    { title: "이벤트2", start: "2024-04-04", end: "2024-04-06" },
  ];

  return (
    <div className="sub-layer">
      <div className="relative top-0 w-full">
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          initialView="dayGridMonth"
          events={myEvents}
          dateClick={handleDateClick}
        />
      </div>

      <div className=" flex h-[240px] w-full flex-col items-start border-t-2 p-3">
        {/* 선택한 날짜에 대한 기념일 목록 출력 */}
        {selectedEvents.map((event, index) => (
          <div key={index}>😘{event.title}</div>
        ))}
      </div>
    </div>
  );
}

export default Calendar;
