function Calendar() {
    return (
        <div className=" flex flex-col items-center w-[360px] h-[640px] bg-white">
            <div className=" flex flex-col justify-center items-center w-full h-[320px] bg-sky-400 border-b-2 border-black">
                <p className=" text-3xl font-cusFont5">달력 들어올 예정...</p>
            </div>

            <div className=" flex flex-col justify-center items-center w-full h-[180px]">
                <p className=" text-3xl font-cusFont5">기념일 목록 출력</p>
            </div>
        </div>
    )
}

export default Calendar;