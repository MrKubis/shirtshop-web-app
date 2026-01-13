import { useEffect, useState } from "react";
import { Item, ItemImage } from "../../models/Item";
import api from "../../api/api";
import "./ItemOrderDisplay.css"

export default function ItemOrderDisplay(props: {item:Item, quantity :number, size : string}){
    const item = props.item;
    const size = props.size;
    const quantity = props.quantity;
    const [url ,setUrl] = useState<string | null> (null);
    const [itemImages, setItemImages] = useState<Array<ItemImage> | null> (null);

    const [totalprice, setTotalprice] = useState<number>(quantity * parseFloat(item.price.toString()))

    console.log(totalprice);
    
    useEffect(()=>{
        setTotalprice(quantity * parseFloat(item.price.toString()))
    },[item.price,props.quantity])

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
    
    return (
        <div className="item-container">
            <div className="image-container">
                {url && <img src={url}/>}
            </div>
            <div className="info-container">
                <p>{item.name}</p>
                <p>{size}</p>
                <p>Ilość : {quantity}</p>
                <p>{totalprice}zł</p>
            </div>
        </div>
    )
}