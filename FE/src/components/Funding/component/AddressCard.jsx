import React from "react";
import { deleteAddress } from "../api/AddressAPI";
function AddressCard({
  name,

  isDefault,

  defaultAddr,
  detailAddr,
  zipCode,
  isSelected,
  onSelect,
  id,
}) {
  const handelDelete = (event) => {
    deleteAddress(localStorage.getItem("access-token"), id);
  };

  return (
    <div className="cursor-pointer flex-col items-center justify-center border-b border-black   py-2">
      <div id="adressCardHead" className="flex  items-center justify-between">
        <div
          id="left "
          className={
            isSelected ? `mx-2 flex text-cusColor3` : `mx-2 flex text-black`
          }
        >
          <p className="">{name} </p>
          <p className="ml-2">{isDefault ? "기본 배송지" : ""} </p>
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

      <div className="mb-2 flex">
        <p className="mx-1">{defaultAddr}</p>
        <p className="mx-1">{detailAddr}</p>
        <p className="mx-1">{zipCode}</p>
      </div>

      <div id="buttonSection" className="flex">
        <button className="mx-2  flex w-[45pt] justify-center rounded-md bg-[#9B9B9B] text-white">
          수정
        </button>
        <button
          onClick={handelDelete}
          className="mx-2  flex w-[45pt] justify-center rounded-md bg-[#9B9B9B] text-white"
        >
          삭제
        </button>
      </div>
    </div>
  );
}

export default AddressCard;
