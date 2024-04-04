import anniversaryData from "../data";

function AnniversaryCard({
  name,
  anniversaryName,
  onSelect,
  anniversaryDate,
  isSelected,
}) {
  return (
    <div className=" cursor-pointer flex-col items-center justify-center border-b border-black   py-2">
      <div id="adressCardHead" className="flex  items-center justify-between">
        <div
          id="left "
          className={
            isSelected ? `mx-2 flex text-cusColor3` : `mx-2 flex text-black`
          }
        >
          <p>{name} </p>
          <p className="mx-2">{anniversaryName}</p>
        </div>
        <div id="right" className="m-2">
          <button
            className={
              isSelected
                ? `flex
                    w-[45pt] justify-center rounded-md bg-cusColor3 text-white`
                : `flex  w-[45pt] justify-center rounded-md bg-[#9B9B9B] text-white`
            }
            onClick={onSelect}
          >
            선택
          </button>
        </div>
      </div>

      <p className="mx-2 mb-2">{anniversaryDate}</p>

      <div id="buttonSection" className="flex">
        <button className="mx-2  flex w-[45pt] justify-center rounded-md bg-[#9B9B9B] text-white">
          수정
        </button>
        <button className="mx-2  flex w-[45pt] justify-center rounded-md bg-[#9B9B9B] text-white">
          삭제
        </button>
      </div>
    </div>
  );
}

export default AnniversaryCard;
