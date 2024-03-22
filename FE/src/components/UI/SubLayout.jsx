import SubHeader from "./SubHeader.jsx";
import { Outlet } from "react-router-dom";

function SubLayout() {
    return (
        <>
        <SubHeader />
        <Outlet />
        </>
    )
}
export default SubLayout