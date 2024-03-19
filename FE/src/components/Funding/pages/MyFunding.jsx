import { BsPeopleFill } from "react-icons/bs";
import { IoMdSettings } from "react-icons/io";
import SearchBar from "../../UI/SearchBar";
import CardList from "../component/CardList";
import { useState } from 'react'



function MyFunding() {
    const [buttonSelected, setButtonSelected] = useState(true);

    const handleClickButton = () => {
        setButtonSelected(!buttonSelected);
    }


    return(
        <div>
            <div id = "profileSection" className="m-2 flex justify-between">
                <div id = "leftSection" className="flex space-x-4">
                    <img src="src\components\Funding\assets\egg3.jpg" alt="프로필" className="w-10 h-10" />
                    <p>김싸피</p>
                </div>
                <div id = "rightSection" className="flex space-x-0">
                    <div id = "friendButton" >
                        <BsPeopleFill />
                        <p>친구</p>
                    </div>
                    <div id = "settingButton">
                        <IoMdSettings />
                        <p>설정</p>
                    </div>
                </div>

            </div>
            <div id = "buttonSection" className="flex justify-between  ">
                <button onClick = {handleClickButton} name = "myFunding"   className={buttonSelected ? `w-3/6 p-4 bg-cusColor3 `: `w-3/6 p-4 border-b-2 border-cusColor3` } >내가 만든 펀딩</button>
                <button onClick = {handleClickButton} name = "friendsFunding"  className={ !buttonSelected ? `w-3/6 p-4 bg-cusColor3 `: `w-3/6 p-4 border-b-2 border-cusColor3` }>내가 참여한 펀딩</button>
            </div>

            <div id = "mainSection">
                <div id = "searchSection" className="p-4">
                    <SearchBar/>
                </div>``
                <div id="cardSection" className="p-4">  
                {
                    buttonSelected ? <CardList data = {data}/> : <CardList data = {data2}/>
                }
                 
                </div>
            </div>
        </div>
    );
}


let data = [
    {
        id : 0,
        title : "GAME IS MY LIFE",
        name : "닌텐도 스위치",
        date : "2024.4.15 ~ 2024.4.22",
        progress : 10
    },
  
    {
        id : 1,
        title : "HEALTH IS MY LIFE",
        name : "단백질 세트",
        date : "2024.4.15 ~ 2024.4.22",
        progress : 70
    },
  
    {
        id : 2,
        title : "MUSIC IS MY LIFE",
        name : "에어팟 맥스",
        date : "2024.4.15 ~ 2024.4.22",
         progress : 100
    },
    {
        id : 3,
        title : "FOOD IS MY LIFE",
        name : "고오급 케이크",
        date : "2024.4.15 ~ 2024.4.22",
        progress : 20
    }
  ]

  let data2 = [
    {
        id : 0,
        title : "GAME IS MY LIFE",
        name : "닌텐도 스위치",
        date : "2024.4.15 ~ 2024.4.22",
        progress : 10
    },
  
    {
        id : 1,
        title : "HEALTH IS MY LIFE",
        name : "단백질 세트",
        date : "2024.4.15 ~ 2024.4.22",
        progress : 70
    },
  
    {
        id : 2,
        title : "MUSIC IS MY LIFE",
        name : "에어팟 맥스",
        date : "2024.4.15 ~ 2024.4.22",
         progress : 40
    },

  ]

export default MyFunding

