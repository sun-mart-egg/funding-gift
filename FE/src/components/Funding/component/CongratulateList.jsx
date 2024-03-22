import CongratulateCard from "./CongratulateCard";

function CongratulateList({ listData, onCardClick }) {
    return (
        <div id="congratulateList" className="w-full flex-col justify-center">
            {listData.map((message, index) => (
                <CongratulateCard
                    key={index}
                    name={message.name}
                    title={message.title}
                    onClick={() => onCardClick(message)}
                />
            ))}
        </div>
    );
}

export default CongratulateList;
