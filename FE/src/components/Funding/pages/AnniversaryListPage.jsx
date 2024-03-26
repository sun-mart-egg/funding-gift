import AnniversaryList from "../component/AnniversaryList";
import anniversaryData from "../data";
function AnniversaryListPage() {
  return (
    <div className="sub-layer font-cusFont3 text-[14px]">
      <AnniversaryList listData={anniversaryData} className="" />
      <button className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white">
        기념일 신규 입력
      </button>
    </div>
  );
}

export default AnniversaryListPage;
