function CongratulateCard({ title, name, onClick }) {
    return (
        <div onClick={onClick} className=" flex-col justify-center items-center border border-black m-2 cursor-pointer">
            <p>{name}님</p>
            <p>{title}</p>
        </div>
    );
}

export default CongratulateCard;
