import SubHeader from "./SubHeader.jsx";
import { Outlet } from "react-router-dom";

function SubLayout() {
  return (
    <div className="flex min-h-screen w-full flex-col">
      <SubHeader />
      <div className="flex-grow ">
        <Outlet />
      </div>
    </div>
  );
}
export default SubLayout;
