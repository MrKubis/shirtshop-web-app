import { useEffect, useState } from "react"
import api from "../api/api"
import { Item, ItemImage } from "../models/Item"
import ItemImagePanel from "../components/panels/ItemImagePanel"
import "./styles/Page.css"
import { NavLink } from "react-router"
import { LineWobble } from 'ldrs/react'
import 'ldrs/react/LineWobble.css'

export default function ShirtsPage(){

    const [shirts, setShirts] = useState<Array<Item> | null > (null)
    const [isLoading, setLoading ] = useState<boolean> (true)

    useEffect(()=>{
        setLoading(true);
        api.get("/items/type/shirt")
        .then((response)=>{
            setShirts(response.data);
        })
        .finally(() => {
            setLoading(false);
        }
        );
    },[])


    if(isLoading) return <div>
        <LineWobble
                            size="100"
                            stroke="8"
                            bgOpacity="0.1"
                            speed="1.75"
                            color="black" 
                            />
                            <p>Loading...</p>
        </div>

    return(
        <ul>
        {
            shirts?.map(shirt =>
                <li key={shirt.id}>
                    <NavLink to={`/shirts/${shirt.id}`}>
                        <ItemImagePanel item = {shirt} />
                    </NavLink>
                </li>
            )
        }
        </ul>
    )
}