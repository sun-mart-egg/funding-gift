import { MdCelebration } from "react-icons/md";

function CongratulateCard({ title, name, onClick }) {
  return (
    <div
      onClick={onClick}
      className="m-2 my-3 flex cursor-pointer flex-col  items-center justify-center rounded-lg border border-gray-400 p-2 font-cusFont3 text-xs"
    >
      <div className="flex ">
        <p>{title}</p> <MdCelebration className="ml-2 size-4 text-cusColor3" />
      </div>

      <p>By {name}</p>
    </div>
  );
}

export default CongratulateCard;
