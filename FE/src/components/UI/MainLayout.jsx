import MainHeader from "./MainHeader.jsx";
import { Outlet } from "react-router-dom";

function MainLayout() {
    return (
        <>
        <MainHeader />
        <Outlet />
        </>
    )
}

export default MainLayout