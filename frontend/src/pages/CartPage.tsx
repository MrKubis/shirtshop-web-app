import { useShoppingCart } from "../components/context/ShoppingCartContext"
import OrderSummary from "../components/OrderSummary";
import ItemOrderDisplay from "../components/panels/ItemOrderDisplay";
import "./styles/CartPage.css"
export default function CartPage(){
    const {cartItems} = useShoppingCart()
    return(
        <div className="page-container">
        <div className="items-container">
        <ul>
            {cartItems && cartItems.map(cartItem  => {
                return  <li>
                            <ItemOrderDisplay key={`${cartItem.item.id}-${cartItem.size}`} item  = {cartItem.item} quantity = {cartItem.quantity} size = {cartItem.size} />
                        </li>
            })}
        </ul>
        </div>

        <div className="summary-container">
            <OrderSummary/>
        </div>
        </div>
    )
}