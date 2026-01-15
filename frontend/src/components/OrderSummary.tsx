
import { useEffect, useState } from "react";
import { useShoppingCart } from "./context/ShoppingCartContext";
import "./OrderSummary.css"

export default function OrderSummary(){
    const {cartItems} = useShoppingCart();
    const [totalPrice, setTotalPrice] = useState<number>(0);
    useEffect(()=>{
        var sum = 0;
        cartItems.forEach(cartItem => {
            sum += cartItem.quantity * parseFloat(cartItem.item.price.toString());
        });
        setTotalPrice(sum);
    },[cartItems])
    return(
        <div>
            <h1>Total price: {totalPrice}zł</h1>
            <h2>Send an order to me</h2>
            <form className="order-form">
                <h4>Your e-mail</h4>
                <input type={"email"}/>
                <br/>
                <h4>Phone number</h4>
                <input type={"tel"}/>
                <br/>
                <input className="input-submit" type="submit" value="Send order"/>
            </form>
        </div>
    )
}