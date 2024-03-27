import React from 'react';

import UpArrow from '/imgs/up-arrow.png'

function ScrollToTopButton2() {
    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth' 
        });
    };

    return (
        <button onClick={scrollToTop} className='rounded-full w-[40px] h-[40px] fixed bottom-[25px] right-[20px]'>
            <img src={UpArrow} alt="" className="w-[100%] h-[100%]"/>
        </button>
    );
}

export default ScrollToTopButton2;
