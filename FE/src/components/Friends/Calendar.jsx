import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid"

function Calendar() {
    return (
        <div className="sub-layer">
            <div className="relative top-7 w-full">
                <FullCalendar plugins={[ dayGridPlugin ]} initialView="dayGridMonth"/>
            </div>

            <div className=" flex flex-col justify-center items-center w-full h-[240px]">
                <br />
                <br />
                <p className=" text-3xl font-cusFont5">기념일 목록 출력</p>
                <br />
                <p className=" text-3xl font-cusFont5">이제 여기에</p>
                <p className=" text-3xl font-cusFont5">캘린더 이벤트들을</p>
                <p className=" text-3xl font-cusFont5">어떻게 연동시키느냐?</p>
                <p className=" text-3xl font-cusFont5">🤔</p>
            </div>
        </div>
    )
}

export default Calendar;