import { useEffect, useState } from "react";
import { Item, ItemImage } from "../../models/Item";
import "./ItemImagePanel.css"
import api from "../../api/api";
export default function ItemImagePanel(props:{item : Item}){
    const item = props.item;
    const [url ,setUrl] = useState<string | null> (null);
    const [itemImages, setItemImages] = useState<Array<ItemImage> | null> (null);

    useEffect(()=>{
        api.get(`http://localhost:8080/api/images/item/${item.id}`)
        .then((response)=>{
            setItemImages(response.data);
        })
    },[])
    
    useEffect(()=>{
        if (!itemImages) return;
        itemImages.forEach(itemImage =>{
            if(itemImage.name.startsWith("main")){
                setUrl(`http://localhost:8080/api/images/download/${itemImage.imageId}`)
            }
        })
    },[itemImages])

    return(
        <div className="item-container">
            {url && <img className="item-img" src = {url}/>}
            <p>{ item.name.toUpperCase()}</p>
        </div>
    )
}
