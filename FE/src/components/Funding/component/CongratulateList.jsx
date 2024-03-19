import CongratulateCard from "./CongratulateCard";

function CongratulateList({ listData, onCardClick }) {
    return (
        <div id="congratulateList" className="w-full flex-col justify-center">
            {listData.map((listData, index) => (
                <CongratulateCard
                    key={index}
                    name={listData.name}
                    title={listData.title}
                    onClick={() => onCardClick()}
                />
            ))}
        </div>
    );
}

export default CongratulateList;
