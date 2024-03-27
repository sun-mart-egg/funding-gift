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
    <div className="sub-layer font-cusFont3 text-[14px]">
      <AccounList listData={data} />
      <button className="fixed bottom-5  h-[45px] w-[80%]  rounded-md bg-cusColor3 text-white">
        신규 계좌 입력
      </button>
    </div>
  );
}

export default AccountListPage;
