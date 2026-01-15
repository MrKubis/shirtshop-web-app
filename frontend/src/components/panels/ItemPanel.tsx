import { useState } from "react";
import { Item, ItemImage } from "../../models/Item";
import { useShoppingCart } from "../context/ShoppingCartContext";

export default function ItemPanel(props:{item:Item, itemImages:ItemImage[]}){

    const item = props.item;
    const itemImages = props.itemImages;
    const {
    increaseItemQuantity,
    } = useShoppingCart()
    
    const [selectedSize, setSelectedSize] = useState<string>("S")

    function handleAdd (){
        increaseItemQuantity(item,selectedSize);
    }
    return(
        <>
        <div className="images-container">
            <ul className="image-list">
                {itemImages.map(itemImage =>(<li key={itemImage.imageId}>
                    <img src={`http://localhost:8080/api/images/download/${itemImage.imageId}`} />
                </li>))}
            </ul>
        </div>
        <div className="info-container">
            <h1 className="text-name">{item?.name}</h1>
            <p className="text-description">{item?.description}</p>
            <h3 className="text-price">{item?.price.toString()} zł</h3>
               <select 
                    value={selectedSize} 
                    onChange={(e) => setSelectedSize(e.target.value)}
                >
                <option>S</option>
                <option>M</option>
                <option>L</option>
                <option>XL</option>
            </select>
            <button onClick = {handleAdd}>Add to cart</button>
        </div>
        </>    

    );
}