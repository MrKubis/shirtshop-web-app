import { useEffect, useState } from "react";
import { useParams } from "react-router";
import api from "../api/api";
import { Item, ItemImage } from "../models/Item";
import { UUID } from "crypto";
import { LineWobble } from "ldrs/react";
import 'ldrs/react/LineWobble.css'
import "./styles/ShirtPage.css"
import { useShoppingCart } from "../components/context/ShoppingCartContext";
import ItemPanel from "../components/panels/ItemPanel";
export default function ShirtPage(){

    const id = useParams().id as UUID;
    const [itemImages,setItemImages] = useState<Array<ItemImage>> ([]);
    const [item, setItem] = useState<Item | null> (null);
    const [isLoading, setLoading] = useState<boolean> (true);
    const {getItemQuantity, increaseItemQuantity,decreaseItemQuantity,removeFromCart} = useShoppingCart();

    //get item info
    useEffect(()=>{
        api.get(`/items/id/${id}`)
        .then(response =>{
            setItem(response.data);
        })
        .finally(()=>{
            setLoading(false);
        })
    },[])

    //get item images
    useEffect(()=>{
    function fetchItemImages( itemId: UUID){
        api.get(`/images/item/${itemId}`)
        .then(response =>{
         setItemImages(response.data);    
        })
    }
    fetchItemImages(id);
    },[])

    if(isLoading) return  <div>
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
        <div className="item-page-container">
            {item && <ItemPanel item={item} itemImages = {itemImages}/>} 
        </div>
    );
}