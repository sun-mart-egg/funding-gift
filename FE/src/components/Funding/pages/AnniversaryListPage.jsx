import AnniversaryList from "../component/AnniversaryList";
import anniversaryData from "../data";
function AnniversaryListPage({ listData }) {
  return (
    <div>
      <AnniversaryList listData={anniversaryData} />
      <button className="bg-cusColor3 text-white">기념일 신규 입력</button>
    </div>
  );
}

export default AnniversaryListPage;
