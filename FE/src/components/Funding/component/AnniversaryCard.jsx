function AnniversaryCard({ name, anniversary, onSelect, date, isSelected }) {
    return (
        <div className="flex-col justify-center items-center border border-black m-2 cursor-pointer">
            <div id="adressCardHead" className="flex">
                <div id="left " className={isSelected ? `text-cusColor3 flex` : `flex text-black`}>
                    <p>{name} </p>
                    <p>{anniversary}</p>
                </div>
                <div id="right">
                    <button className={isSelected ? `bg-cusColor3 flex` : `flex bg-gray-500`} onClick={onSelect}>
                        선택
                    </button>
                </div>
            </div>

            <p>{date}</p>

            <div id="buttonSection">
                <button>수정</button>
                <button>삭제</button>
            </div>
        </div>
    );
}

export default AnniversaryCard;
