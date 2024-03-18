function ProgressBar({ progress }) {
    return (
        <div className="container flex flex-col items-center justify-center w-full relative">
            <div className="progressBar bg-gray-500 w-3/4 h-8 flex items-center justify-center relative">
                <div className="progressBar-fill bg-cusColor6 absolute inset-0" style={{ width: `${progress}%` }}></div>
                <div className="progress-label mt-2 absolute inset-0 flex items-center justify-start">{progress}%</div>
            </div>
        </div>
    );
}

export default ProgressBar;
