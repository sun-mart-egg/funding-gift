import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useEffect, useRef, useState } from "react";
import axios from "axios";

function Calendar() {
  const [selectedEvents, setSelectedEvents] = useState([]); // 선택한 날짜에 있는 행사목록
  const [selectedDay, setSelectedDay] = useState(null) // 선택한 날짜에 대한 useState
  const [events, setEvents] = useState([]) // 친구들의 전체 펀딩목록

  // axios 요청을 위한 연도, 월 데이터
  const [curYear, setCurYear] = useState("")
  const [curMonth, setCurMonth] = useState("")

  // today 버튼 로직을 위한 캘린더 참조 ref
  const calendarRef = useRef(null)

  // 풀캘린더에서 날짜 받아서 split한 값을 curYear, curMonth에 할당
  const handleCurDate = (dateInfo) => {
    const year = dateInfo.startStr.split("-")[0]; // 연도 추출
    let month = parseInt(dateInfo.startStr.split("-")[1], 10); // 월 추출
    month += 1 // 월 데이터가 0 부터 11로 되어있다. 그래서 1을 더해줘야 한다.
    const formatMonth = month < 10 ? `0${month}` : `${month}` // 월을 01, 02 이런식으로 표현하도록 변경
    setCurYear(year)
    setCurMonth(formatMonth)
  }

  // axios 요청을 통한 친구들의 펀딩 목록 가져오기
  useEffect(() => {
    axios.get(import.meta.env.VITE_BASE_URL + "/api/fundings/calendar", {
      params: {
        "year": curYear,
        "month": curMonth,
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access-token")}`,
      }
    })
      .then((res) => {
        console.log(res)
        const formatEvents = res.data.data.map((item) => ({
          title: item.title,
          date: item.anniversaryDate,
          name: item.consumerName,
          display: "background"
        }))
        setEvents(formatEvents)
        console.log("친구들의 펀딩목록 받아오기 성공")

        // 페이지 접속 시, 오늘 날짜의 이벤트 목록들이 바로 출력되도록 설정
        const today = new Date().toISOString().split("T")[0] // Date 정보에서 2024-04-02 만 추출
        const todayEvents = formatEvents.filter((event) => event.date === today) // 오늘 날짜랑 event의 날짜랑 같은것만 필터링
        setSelectedEvents(todayEvents)
        setSelectedDay(today)

      })
      .catch((err) => {
        console.error(err)
        console.log("친구들의 펀딩목록 받아오기 실패")
      })
  }, [curYear, curMonth])

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
    const today = new Date() // 현재 시간 정보 받아오기
    const todayStr = today.toISOString().split("T")[0] // 날짜만 받아온다
    console.log(todayStr)
  }

  return (
    <div className="sub-layer">
      <div className="absolute w-full top-[65px]">
        <FullCalendar
          ref={calendarRef}
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
              text: `${new Date().toISOString().split("T")[0].split("-")[2]}`, // 오늘 날짜를 today 글자 대신 표현
              click: handleClickToday
            }
          }}
          datesSet={handleCurDate}
          eventContent={() => {
            return ""
          }}
          eventBackgroundColor="#FD7676"
        />
      </div>

      <div className="absolute bottom-0 z-10 flex flex-col w-full h-[200px] gap-4 p-3 overflow-y-scroll text-2xl bg-white border-t-2 signup-font">
        {selectedDay}
        {/* 선택한 날짜에 대한 기념일 목록 출력 */}
        {selectedEvents.length > 0 ? (
          selectedEvents.map((event) => (
            <div key={event.fundingId}>
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
