import { useShoppingCart } from "../components/context/ShoppingCartContext"
import ItemOrderDisplay from "../components/panels/ItemOrderDisplay";

export default function CartPage(){
    const {cartItems} = useShoppingCart()
    console.log(cartItems);
    return(
        
        <>
        {cartItems && cartItems.map(cartItem => {
            return <ItemOrderDisplay item  = {cartItem.item} quantity = {cartItem.quantity} size = {cartItem.size} />
        })}
        </>
    )
}