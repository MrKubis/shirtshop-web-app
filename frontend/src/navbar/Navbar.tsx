
import { NavLink } from "react-router";
import "./Navbar.css"
import { useEffect } from "react";
import api from "../api/api";



export default function Navbar(){

    return(
        <div className="navbar-container">
            <div className="navbar-pages">
                <NavLink to="">
                    <h1>Home</h1>
                </NavLink>
                <NavLink to="shirts">
                    <h1>Shirts</h1>
                </NavLink>
            </div>
            <div className="navbar-account">
                <NavLink to="cart">
                    <h1>Cart</h1>
                </NavLink>
            </div>
        </div>
    )
}