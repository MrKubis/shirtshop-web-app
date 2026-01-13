import { UUID } from "crypto";
import { useShoppingCart } from "../components/context/ShoppingCartContext";

export type Item = {
    id:UUID,
    name:string,
    type:string,
    description:string,
    price:Number,
    images: Array<ItemImage> | null
}

export type ItemImage = {
    id:UUID,
    imageId:UUID,
    name:string
}
