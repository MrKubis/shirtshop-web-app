import { NavLink } from "react-router";
import LoginForm from "../../components/forms/LoginForm";
import "./LoginPage.css"

export default function LoginPage(){
    return(
        <div>
            <LoginForm/>
            <NavLink to="/register">Register</NavLink>
        </div>

    )
}