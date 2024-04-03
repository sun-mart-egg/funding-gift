import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useEffect, useRef, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Calendar() {
  const [selectedEvents, setSelectedEvents] = useState([]); // 선택한 날짜에 있는 행사목록
  const [selectedDay, setSelectedDay] = useState(null); // 선택한 날짜에 대한 useState
  const [events, setEvents] = useState([]); // 친구들의 전체 펀딩목록
  const navigate = useNavigate();

  // axios 요청을 위한 연도, 월 데이터
  const [curYear, setCurYear] = useState("");
  const [curMonth, setCurMonth] = useState("");

  // today 버튼 로직을 위한 캘린더 참조 ref
  const calendarRef = useRef(null);

  // 한국 시간으로 변환
  const KoreaTime = () => {
    const koreaTimeFormat = new Intl.DateTimeFormat("ko-KR", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      timeZone: "Asia/Seoul",
    });
    const today = new Date();
    const formattedDate = koreaTimeFormat
      .format(today)
      .replace(/\. /g, "-")
      .replace(/\./, "");
    return formattedDate; // 'YYYY-MM-DD' 형태로 반환
  };
  
  // 풀캘린더에서 날짜 받아서 split한 값을 curYear, curMonth에 할당
  const handleCurDate = (dateInfo) => {
    // 연도와 월의 형식이 2024-04-02로 들어오는데
    // "-"를 기준으로 나눈 후에, 10진수의 정수로 반환한다.
    let year = parseInt(dateInfo.startStr.split("-")[0], 10); // 년도
    let month = parseInt(dateInfo.startStr.split("-")[1], 10) // 월
    let day = parseInt(KoreaTime().split("-")[2], 10) // 일
    
    console.log(year, month, day)
    setCurYear(year);
    setCurMonth(month);
  };

  // axios 요청을 통한 친구들의 펀딩 목록 가져오기
  useEffect(() => {
    axios
      .get(import.meta.env.VITE_BASE_URL + "/api/fundings/calendar", {
        params: {
          year: curYear,
          month: curMonth,
        },
        headers: {
          Authorization: `Bearer ${localStorage.getItem("access-token")}`,
        },
      })
      .then((res) => {
        console.log(res);
        const formatEvents = res.data.data.map((item) => ({
          title: item.title,
          date: item.anniversaryDate,
          name: item.consumerName,
          fundingId: item.fundingId,
          display: "background",
        }));
        setEvents(formatEvents);
        console.log("친구들의 펀딩목록 받아오기 성공");

        // 페이지 접속 시, 오늘 날짜의 이벤트 목록들이 바로 출력되도록 설정
        const today = KoreaTime(); // 한국 날짜 기준으로 받아오기.
        const todayEvents = formatEvents.filter(
          (event) => event.date === today,
        ); // 오늘 날짜랑 event의 날짜랑 같은것만 필터링
        setSelectedEvents(todayEvents);
        setSelectedDay(today);
      })
      .catch((err) => {
        console.error(err);
        console.log("친구들의 펀딩목록 받아오기 실패");
      });
  }, [curYear, curMonth]);

  // 캘린더에서 날짜 선택 시
  const handleDateClick = (arg) => {
    const clickedDate = arg.dateStr;
    const ThisDate = events.filter((event) => {
      const clickDay = event.date;
      return clickedDate === clickDay;
    });
    setSelectedEvents(ThisDate);
    setSelectedDay(clickedDate);
    console.log(clickedDate)
  };

  // 캘린더 header에서 'today' 버튼 선택 시 오늘 날짜로 focus
  const handleClickToday = () => {
    const calendarApi = calendarRef.current.getApi(); // 풀캘린더 api 인스턴스를 가져온다
    calendarApi.today(); // 캘린더를 오늘 날짜로 이동시킨다

    const today = KoreaTime(); // 한국 시간 반영
    const todayEvents = events.filter((event) => event.date === today); // 오늘 날짜에 해당하는 이벤트 필터링

    setSelectedEvents(todayEvents); // 오늘 날짜 이벤트로 상태 업데이트
    setSelectedDay(today); // 오늘 날짜로 상태 업데이트
  };

  return (
    <div className="sub-layer">
      <div className="absolute top-[65px] w-full">
        <FullCalendar
          ref={calendarRef}
          plugins={[dayGridPlugin, interactionPlugin]}
          initialView="dayGridMonth"
          events={events}
          dateClick={handleDateClick}
          locale="kr"
          headerToolbar={{
            left: "prev",
            center: "title",
            right: "today next",
          }}
          customButtons={{
            today: {
              text: KoreaTime().split("-")[2], // 오늘 날짜를 today 글자 대신 표현
              click: handleClickToday,
            },
          }}
          datesSet={handleCurDate}
          eventContent={() => {
            return "";
          }}
          eventBackgroundColor="#FD7676"
          showNonCurrentDates={false}
        />
      </div>

      <div className="signup-font absolute bottom-0 z-10 flex h-full max-h-[205px] w-full flex-col gap-4 overflow-y-scroll border-t-2 bg-white p-3 text-2xl">
        {selectedDay}
        {/* 선택한 날짜에 대한 기념일 목록 출력 */}
        {selectedEvents.length > 0 ? (
          selectedEvents.map((event, index) => (
            <div
              key={index}
              onClick={() =>
                navigate(`/friend-funding-detail/${event.fundingId}`)
              }
            >
              <p>
                😘 {event.name}의 {event.title}
              </p>
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
