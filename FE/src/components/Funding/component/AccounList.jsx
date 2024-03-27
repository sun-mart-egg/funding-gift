import React, { useState } from "react";
import AccountCard from "./AccountCard";

function AccounList({ listData }) {
  // listData 상태 관리
  const [data, setData] = useState(listData);

  // isSelected 상태를 업데이트하는 함수
  const handleSelect = (selectedIndex) => {
    const newData = data.map((item, index) => ({
      ...item,
      isSelected: index === selectedIndex,
    }));
    setData(newData);
  };

  return (
    <div
      id="accountList"
      className="absolute top-20 w-full flex-col justify-center"
    >
      {data.map((item, index) => (
        <AccountCard
          key={index}
          name={item.name}
          isDefault={item.isDefault}
          bank={item.bank}
          account={item.account}
          isSelected={item.isSelected}
          onSelect={() => handleSelect(index)} // onSelect prop 추가
        />
      ))}
    </div>
  );
}

export default AccounList;
