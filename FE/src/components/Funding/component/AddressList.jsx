import React, { useState } from "react";
import AddressCard from "./AddressCard";

function AddressList({ listData }) {
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
      id="addressList"
      className="absolute top-20 w-full flex-col justify-center"
    >
      {data.map((item, index) => (
        <AddressCard
          key={index}
          name={item.name}
          nickname={item.nickname}
          isDefault={item.isDefault}
          phone={item.phone}
          address={item.address}
          isSelected={item.isSelected}
          onSelect={() => handleSelect(index)} // onSelect prop 추가
        />
      ))}
    </div>
  );
}

export default AddressList;
