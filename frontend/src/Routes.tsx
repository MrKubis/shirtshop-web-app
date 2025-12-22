import { RouteObject } from "react-router";
import App from "./App";

export const routes: RouteObject[] =[
    {
        path:"/",
        element:<App/>,
        children:[
            {}
        ]
    }
]