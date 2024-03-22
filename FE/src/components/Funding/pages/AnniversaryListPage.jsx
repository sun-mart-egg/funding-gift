import AnniversaryList from "../component/AnniversaryList";

function AnniversaryListPage() {
  const data = [
    {
      name: "신시은",
      anniversary: "생일",
      date: "2024.4.22",
      isSelected: true,
    },
    {
      name: "박창준",
      anniversary: "생일",
      date: "2024.2.21",
      isSelected: false,
    },
  ];
  return (
    <div className="sub-layer">
      <AnniversaryList listData={data} />
      <button className="bg-cusColor3 text-white">기념일 신규 입력</button>
    </div>
  );
}

export default AnniversaryListPage;
