import { useEffect, useState } from "react";
import { Item } from "../../models/Item";
import "./ItemImagePanel.css"
export default function ItemImagePanel(props:{item : Item}){
    const item = props.item;
    const [url ,setUrl] = useState<string | null> (null);
    useEffect(()=>{
        item.images?.forEach(itemImage =>{
            if(itemImage.name.startsWith("main")){
                console.log(itemImage.name);
                setUrl(`http://localhost:8080/api/images/download/${itemImage.imageId}`)
            }
        })
    })


    console.log(item);
    return(
        <div className="item-container">
            {url && <img className="item-img" src={url}/>}
            <p>{ item.name.toUpperCase()}</p>
        </div>
    )
}