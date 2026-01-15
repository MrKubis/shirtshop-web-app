import {Plus, Minus , X, Trash2} from "lucide-react"
import { useEffect, useState } from "react";
import { Item, ItemImage } from "../../models/Item";
import api from "../../api/api";
import "./ItemOrderDisplay.css"
import { useShoppingCart } from "../context/ShoppingCartContext";

export default function ItemOrderDisplay(props: {item:Item, quantity :number, size : string}){

    const { increaseItemQuantity ,decreaseItemQuantity,removeFromCart} = useShoppingCart();
    const item = props.item;
    const size = props.size;
    const quantity = props.quantity;
    
    const [url ,setUrl] = useState<string | null> (null);
    const [itemImages, setItemImages] = useState<Array<ItemImage> | null> (null);

    const [totalprice, setTotalprice] = useState<number>(quantity * parseFloat(item.price.toString()))
    
    function handleIncrease(){
        console.log("increase");
        increaseItemQuantity(item,size);
    }
    function handleDecrease(){
        console.log("decrease");
        decreaseItemQuantity(item,size);
    }
    function handleRemove(){
        console.log("remove");       
        removeFromCart(item,size);
    }

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
                <h3>{item.name}</h3>
                <p>Size : {size}</p>
                <p>Quantity : {quantity}</p>
                <p>Price : {totalprice}zł</p>
            </div>
            <div className="modify-item-container">
                <button><Plus size ={20} onClick={handleIncrease}/></button>
                <button><Minus size = {20} onClick={handleDecrease}/></button>
                <button><Trash2 size = {18} onClick={handleRemove}/></button>
            </div>
        </div>
    )
}