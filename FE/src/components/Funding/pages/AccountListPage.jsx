import React from "react";
import AccounList from "../component/AccounList";

function AccountListPage() {
  const data = [
    {
      name: "신시은",
      account: "XXXX-XXXXX-XXXXX",
      bank: "하나은행",
      isDefault: true,
      isSelected: true,
    },
    {
      name: "박창준",
      account: "ooo-ooooo-ooooo",
      bank: "국민은행",
      isDefault: false,
      isSelected: false,
    },
  ];
  return (
    <div className="sub-layer">
      <AccounList listData={data} />
      <button className="bg-cusColor3 text-white">신규 계좌 입력</button>
    </div>
  );
}

export default AccountListPage;
