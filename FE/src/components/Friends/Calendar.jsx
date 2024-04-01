import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useEffect, useState } from "react";
import axios from "axios";

function Calendar() {
  // 이벤트 목록 받아올 배열
  const [selectedEvents, setSelectedEvents] = useState([]);
  const [selectedDay, setSelectedDay] = useState(null)
  const [events, setEvents] = useState([])

  // axios 요청을 통한 친구들의 펀딩 목록 가져오기
  useEffect(() => {
    axios.get(import.meta.env.VITE_BASE_URL + "/api/fundings/calendar", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access-token")}`,
      },
    })
    .then((res) => {
      console.log(res)
      const formatEvents = res.data.data.map((item) => ({
        title: item.title,
        date: item.anniversaryDate,
        name: item.consumerName,
      }))
      setEvents(formatEvents)
      console.log("친구들의 펀딩목록 받아오기 성공")
      
    })
    .catch((err) => {
      console.error(err)
      console.log("친구들의 펀딩목록 받아오기 실패")
    })
  }, [])

  // 캘린더 날짜 선택 시
  const handleDateClick = (arg) => {
    const clickedDate = arg.dateStr;
    const ThisDate = events.filter((event) => {
      const clickDay = event.date
      return clickedDate === clickDay
    })
    setSelectedEvents(ThisDate)
    setSelectedDay(clickedDate)
  };

  // 캘린더 today 선택 시
  const handleClickToday = () => {
    const today = new Date()
    console.log(today)
    const todayStr = today.toISOString().split("T")[0]
    console.log(todayStr)
  }

  return (
    <div className="sub-layer">
      <div className="absolute w-full top-[65px]">
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          initialView="dayGridMonth"
          events={events}
          dateClick={handleDateClick}
          locale="kr"
          headerToolbar={
            {
              left: "prev",
              center: "title",
              right: "today next"
            }
          }
          customButtons={{
            today: {
              text: "Today",
              click: handleClickToday
            }
          }}
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
