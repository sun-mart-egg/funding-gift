import React, { useState } from "react";
import AccountCard from "./AccountCard";
import { useStore } from "../../Store/MakeStore";
function AccounList({ listData }) {
  // listData 상태 관리
  const [data, setData] = useState(listData);

  //accout index 정보 변수
  const selectedAccountIndex = useStore((state) => state.selectedAccountIndex);
  //account 정보의 index를 받아오는 함수
  const setSelectedAccountIndex = useStore(
    (state) => state.setSelectedAccountIndex,
  );
  //accout 정보를 받아오는 함수
  const setSelectedAccount = useStore((state) => state.setSelectedAccount);

  // isSelected 상태를 업데이트하는 함수
  const handleSelect = (index) => {
    setSelectedAccount(data[index]);
    setSelectedAccountIndex(index);
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
          isSelected={index === selectedAccountIndex}
          onSelect={() => handleSelect(index)} // onSelect prop 추가
        />
      ))}
    </div>
  );
}

export default AccounList;
