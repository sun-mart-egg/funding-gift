import { FaHeart } from "react-icons/fa6";
import { FaCartShopping } from "react-icons/fa6";


function MakeFundingMain(){
    return (
        <div className="bg-cusColor3 h-screen flex justify-center items-center">
            <div id="makeCard" className="w-2/3 h-3/5 rounded-xl bg-white p-4 flex flex-col justify-around">
                <div id="titleSection" className="text-center mb-8">
                    <p>생선가게에서 </p>
                    <p>놀라운 선물을 만들어 보세요</p>
                </div>
                <div id="buttonSection" className="flex justify-around w-full">
                    <button style={{flexBasis: '40%'}} className="bg-cusColor3 rounded-lg"> <FaHeart/> 위시에서 선물 만들기</button>
                    <button style={{flexBasis: '40%'}} className="bg-cusColor4 rounded-lg"> <FaCartShopping/>쇼핑몰에서 선물 만들기</button>
                </div>
            </div>
        </div>
    );
}

export default MakeFundingMain