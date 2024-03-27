import React, { useState } from "react";
import AnniversaryCard from "./AnniversaryCard";
import { useStore } from "../../Store/AnniversaryStore";

function AnniversaryList({ listData }) {
  // listData 상태 관리
  const [data, setData] = useState(listData);
  const setSelectedAnniversary = useStore(
    (state) => state.setSelectedAnniversary,
  );
  const setSelectedIndex = useStore((state) => state.setSelectedIndex);
  const selectedIndex = useStore((state) => state.selectedIndex);

  // isSelected 상태를 업데이트하는 함수
  const handleSelect = (index) => {
    setSelectedAnniversary(listData[index]);
    setSelectedIndex(index);
    console.log(selectedIndex);
  };

  return (
    <div
      id="accountList"
      className="absolute top-20 w-full flex-col justify-center"
    >
      {data.map((item, index) => (
        <AnniversaryCard
          key={index}
          anniversary={item.anniversary}
          name={item.name}
          date={item.date}
          isSelected={index === selectedIndex} // 현재 카드가 선택된 상태인지 판단
          onSelect={() => handleSelect(index)} // onSelect prop 추가
        />
      ))}
    </div>
  );
}

export default AnniversaryList;
