import { useState } from 'react';
import refreshIcon from './refreshIcon.png'
import searchIcon from './searchIcon.png'
import searchIconTrue from './searchIconTrue.png'
import filterIcon from './filterIcon.png'
import filterIconTrue from './filterIconTrue.png'

function Friends() {
    const [isSearch, setIsSearch] = useState(false)
    const [isFilter, setIsFilter] = useState(false)
    const searchState = () => {
        setIsSearch(prevSearch => !prevSearch)
    }
    const filterState = () => {
        setIsFilter(prevFilter => !prevFilter)
    }
    return (
        <div className=" w-[360px] h-[640px] bg-white flex flex-col items-center">
            <div className=" w-[360px] h-[40px] flex flex-row justify-between">
                <div className=" flex flex-row items-center p-3">
                    <p className=" font-cusFont3 font-bold text-lg p-2.5">친구 8</p>
                    <button>
                        <img src={refreshIcon} alt="동기화아이콘" />
                    </button>
                </div>

                <div className=' flex flex-row items-center p-2.5'>
                    {isSearch ? <input type="text" className=' w-[160px] h-[25px] border border-cusColor3 rounded-[10px] m-1' /> : ''}
                    <button onClick={searchState} className=' p-1'>
                        <img src={isSearch ? searchIconTrue : searchIcon} alt="검색아이콘" />
                    </button>
                    <button onClick={filterState} className=' p-1'>
                        <img src={isFilter ? filterIconTrue : filterIcon} alt="필터아이콘" />
                    </button>
                </div>
            </div>
            <div>
                <p className=' text-4xl font-cusFont5'>친구목록 나올거에요</p>
            </div>
        </div>
    )
}

export default Friends;