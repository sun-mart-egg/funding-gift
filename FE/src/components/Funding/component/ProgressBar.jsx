function ProgressBar({ progress }) {
  return (
    <div className="progressBar relative flex h-full w-full items-center justify-center rounded-xl bg-gray-400">
      <div
        className={
          progress === 100
            ? `progressBar-fill absolute inset-0  rounded-xl bg-cusColor4`
            : `progressBar-fill h- absolute inset-0 rounded-xl bg-cusColor6`
        }
        style={{ width: `${progress}%` }}
      ></div>
      <div className=" progress-label absolute inset-0 m-2 flex items-center justify-start text-[10px]">
        {progress}%
      </div>
    </div>
  );
}

export default ProgressBar;
