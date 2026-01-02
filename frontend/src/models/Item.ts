import { UUID } from "crypto";

export interface Item{
    id:UUID,
    name:string,
    type:string
    images: Array<ItemImage> | null
}

export interface ItemImage{
    id:UUID,
    imageId:UUID,
    name:string
}
