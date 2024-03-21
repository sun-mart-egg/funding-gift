import MainHeader from "./MainHeader.jsx";
import Footer from "./Footer.jsx";
import { Outlet } from "react-router-dom";

function MainLayout() {
    const [activeIcon, setActiveIcon] = useState('home');

    return (
        <>
        <MainHeader setActiveIcon={setActiveIcon}/>
        <Outlet />
        <Footer activeIcon={activeIcon} setActiveIcon={setActiveIcon}/>
        </>
    )
}

export default MainLayout