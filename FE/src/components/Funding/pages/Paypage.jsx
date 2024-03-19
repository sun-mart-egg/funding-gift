import React from "react";
import { useLocation } from "react-router-dom"; // useLocation을 import합니다.

function Paypage() {
  const location = useLocation(); // 현재 location 객체를 가져옵니다.
  const { amount } = location.state; // location.state에서 amount 값을 추출합니다.
  return <div>{amount}</div>;
}

export default Paypage;
