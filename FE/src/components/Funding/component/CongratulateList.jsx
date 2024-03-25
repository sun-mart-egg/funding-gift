import CongratulateCard from "./CongratulateCard";

function CongratulateList({ listData, onCardClick }) {
  return (
    <div id="messageSection" className=" w-full flex-col px-7 ">
      <div id="subTitle" className="px-2 font-cusFont2 text-[18px]">
        <p>축하 받은 리스트</p>
      </div>
      <div id="congratulateList" className="w-full flex-col justify-center">
        {listData.map((message, index) => (
          <CongratulateCard
            key={index}
            name={message.name}
            title={message.title}
            onClick={() => onCardClick(message)}
          />
        ))}
      </div>
    </div>
  );
}

export default CongratulateList;
