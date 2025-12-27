import { useEffect, useState } from "react"
import api from "../api/api"
import { Item, ItemImage } from "../models/Item"
import ItemImagePanel from "../components/panels/ItemImagePanel"
import "./styles/Page.css"
import { NavLink } from "react-router"

export default function ShirtsPage(){

//TODO ZAPISYWANIE JAKOŚ ZDJĘĆ I SHIRT PANELS

    const [shirts, setShirts] = useState<Array<Item> | null > (null)
    const [isLoading, setLoading ] = useState<boolean> (true)
    const [isLoaded, setLoaded] = useState<boolean>(false)

    useEffect(()=>{
        setLoading(true);
        api.get("/items/type/shirt")
        .then((response)=>{
            setShirts(response.data);
        })
    },[])

    useEffect(()=>{
        if(!shirts) return;
        shirts.forEach(shirt => {
            fetchItemImages(shirt);
        }
    );
    },[shirts])

    const fetchItemImages = ( item : Item) => {
        api.get(`/images/item/${item.id}`)
        .then((response)=>{
            item.images = response.data;
        })
        .finally(()=>{
            setLoading(false);
            setLoaded(true);
        })
    }

    if(isLoading) return <p>Loading...</p>
    return(
        <ul>
            {isLoaded &&  shirts?.map(shirt =>
                <li>
                    <NavLink to={`/shirts/${shirt.id}`}>
                        <ItemImagePanel key = {shirt.id} item = {shirt} />
                    </NavLink>
                </li>
            )}
        </ul>
    )
}