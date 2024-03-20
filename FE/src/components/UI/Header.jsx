import { Link } from "react-router-dom";

function Header() {
    return (
        <header className="header-layer">
            <Link to={"/"}>
                <img src="/imgs/logo.png" alt="logo" className="pt-1" />
            </Link>

            <header className="flex flex-row items-center">
                <button>
                    <img src="/imgs/heart.png" alt="wish-button" className="m-3" />
                </button>
                <button>
                    <Link to={"/friends/calendar"}>
                        <img src="/imgs/calendar.png" alt="calendar-button" className="m-2" />
                    </Link>
                </button>
                <button>
                    <img src="/imgs/bell.png" alt="alert-button" className="m-2" />
                </button>
            </header>
        </header>
    )
}

export default Header;