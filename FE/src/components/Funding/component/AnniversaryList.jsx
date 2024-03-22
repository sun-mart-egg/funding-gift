import React, { useState } from "react";
import AnniversaryCard from "./AnniversaryCard";

function AnniversaryList({ listData }) {
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
        <div id="accountList" className="w-full flex-col justify-center">
            {data.map((item, index) => (
                <AnniversaryCard
                    key={index}
                    anniversary={item.anniversary}
                    name={item.name}
                    date={item.date}
                    isSelected={item.isSelected}
                    onSelect={() => handleSelect(index)} // onSelect prop 추가
                />
            ))}
        </div>
    );
}

export default AnniversaryList;
