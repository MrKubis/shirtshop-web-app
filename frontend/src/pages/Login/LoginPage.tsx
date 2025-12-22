import { NavLink } from "react-router";
import LoginForm from "../../components/Forms/LoginForm";
import "./LoginPage.css"

export default function LoginPage(){
    return(
        <div>
            <LoginForm/>
            <NavLink to="/register">Register</NavLink>
        </div>

    )
}