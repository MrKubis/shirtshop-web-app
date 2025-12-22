
import { NavLink } from "react-router";
import "./Navbar.css"

export default function Navbar(){
    return(
        <div className="navbar-container">
            <div className="navbar-pages">
                <NavLink to="">
                    <p>Home</p>
                </NavLink>
                <NavLink to="shirts">
                    <p>Shirts</p>
                </NavLink>
            </div>
            <div className="navbar-account">
                <NavLink to="cart">
                    <p>Cart</p>
                </NavLink>
            </div>
        </div>
    )
}