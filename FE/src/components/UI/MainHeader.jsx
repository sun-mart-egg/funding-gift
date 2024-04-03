import { Link } from "react-router-dom";

function Header() {
  return (
    <header className="header w-full justify-between">
      <Link to={"/"} onClick={() => setActiveIcon("home")}>
        <img src="/imgs/logo.png" alt="logo" />
      </Link>

      <header className="flex flex-row gap-3 p-3">
        <button>
          <Link to={"/wishlist"}>
            <img src="/imgs/heart.png" alt="wish-button" />
          </Link>
        </button>
        <button>
          <Link to={"/calendar"}>
            <img src="/imgs/calendar.png" alt="calendar-button" />
          </Link>
        </button>
        <button>
          <Link to={"/alarm"}>
            <img src="/imgs/bell.png" alt="alert-button" />
          </Link>
        </button>
      </header>
    </header>
  );
}

export default Header;
