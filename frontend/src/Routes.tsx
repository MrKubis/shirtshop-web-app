import { createBrowserRouter, Navigate, RouteObject } from "react-router";
import App from "./App";
import LoginPage from "./pages/Login/LoginPage";
import NotFoundPage from "./pages/NotFoundPage";
import ShirtsPage from "./pages/ShirtsPage";
import CartPage from "./pages/CartPage";
import AboutPage from "./pages/AboutPage";
import ShirtPage from "./pages/ShirtPage";

export const routes: RouteObject[] =[
    {
        path:"/",
        element:<App/>,
        children:[
            {
                path:"login",
                element:<LoginPage/>
            },
            {
                path:"about",
                element:<AboutPage/>
            },
            {
                path:"shirts",
                element:<ShirtsPage/>,
            },
            {
                path:"shirts/:id",
                element:<ShirtPage/>
            },
            {
                path:"cart",
                element:<CartPage/>
            },
            {
                path:"not-found",
                element:<NotFoundPage/>
            },
            {path: '*',element :<Navigate replace to="/not-found"/>}

        ]
    }
]
export const router = createBrowserRouter(routes);