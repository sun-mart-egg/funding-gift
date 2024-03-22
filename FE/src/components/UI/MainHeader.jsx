import { Link } from "react-router-dom";

function Header() {
  return (
    <header className="header w-full justify-between">
      <Link to={"/"} onClick={() => setActiveIcon("home")}>
        <img src="/imgs/logo.png" alt="logo" />
      </Link>

      <header className="flex flex-row gap-3 p-3">
        <button>
          <img src="/imgs/heart.png" alt="wish-button" />
        </button>
        <button>
          <Link to={"/friends/calendar"}>
            <img src="/imgs/calendar.png" alt="calendar-button" />
          </Link>
        </button>
        <button>
          <img src="/imgs/bell.png" alt="alert-button" />
        </button>
      </header>
    </header>
  );
}

export default Header;
