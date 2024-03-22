function ProgressBar({ progress }) {
  return (
    <div className="container relative my-2 flex w-full flex-col items-center justify-center">
      <div className="progressBar relative flex h-[14px] w-full items-center justify-center rounded-md bg-gray-500">
        <div
          className={
            progress === 100
              ? `progressBar-fill absolute inset-0 h-[14px] rounded-md bg-cusColor4`
              : `progressBar-fill absolute inset-0 h-[14px] rounded-md bg-cusColor6`
          }
          style={{ width: `${progress}%` }}
        ></div>
        <div className=" progress-label absolute inset-0 mt-2 flex items-center justify-start text-[10px]">
          {progress}%
        </div>
      </div>
    </div>
  );
}

export default ProgressBar;
