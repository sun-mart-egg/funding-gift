import ProgressBar from "./ProgressBar";

function FundingDetailInfo({ title, name, price, detail, progress }) {
    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <div id="fundingTitle">
                <h1>{title}</h1>
            </div>
            <div id="fundingInfoSection" className="flex items-center p-4">
                <div id="fundingImg">
                    <img src="src\components\Funding\assets\egg3.jpg" alt="" />
                </div>
                <div id="fundingInfo" className="p-4">
                    <p>{name}</p>
                    <p>{price}</p>
                    <p>{detail}</p>
                    <button>배송 조회</button>
                </div>
            </div>
            <ProgressBar progress={progress} />
        </div>
    );
}

export default FundingDetailInfo;
