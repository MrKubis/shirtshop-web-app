import { createContext, ReactNode, useContext, useState } from "react";
import { Item } from "../../models/Item";
import { useLocalStorage } from "../../hooks/useLocalStorage";

type CartItem = {
    item: Item
    size : string
    quantity: number
}

type ShoppingCartProviderProps = {
    children: ReactNode
}

type ShoppingCartContext = {
  getItemQuantity: (item : Item,size:string) => number
  increaseItemQuantity: (item : Item,size:string) => void
  decreaseItemQuantity: (item: Item,size:string) => void
  removeFromCart: (item:Item, size:string) => void
  cartItems: CartItem[]
}

const ShoppingCartContext = createContext({} as ShoppingCartContext)

export function useShoppingCart(){
    return useContext(ShoppingCartContext)
}

export function ShoppingCartProvider({ children } : ShoppingCartProviderProps){
    const [cartItems, setCartItems] = useLocalStorage<CartItem[]>("shopping-cart", [])
    
    function increaseItemQuantity(item: Item, size: string){
        setCartItems(currItems =>{
            if( currItems.find(cartItem => cartItem.item.id === item.id && cartItem.size === size) == null){        
                return [...currItems, { item, quantity: 1,size:size }]
            }
            else {
                return currItems.map(cartItem =>{
                    if(cartItem.item.id === item.id){
                        return {...cartItem, quantity: cartItem.quantity + 1 }
                    }
                    else{
                        return cartItem
                    }
                })
            }
        })
    }
    function decreaseItemQuantity(item: Item,size: string){
        setCartItems(currItems =>{
            if( currItems.find(cartItem => cartItem.item.id === item.id && cartItem.size === size) == null){
                return currItems;
            }
            else {
                return currItems.map(cartItem =>{
                    if(cartItem.item.id === item.id){
                        if(cartItem.quantity === 0) return cartItem;
                        return {...cartItem, quantity: cartItem.quantity - 1 };
                    }
                    else{
                        return cartItem;
                    }
                })
            }
        })
    }
    function removeFromCart(item: Item, size:string){
        setCartItems(currItems =>{
            return currItems.filter(cartItem => cartItem.item.id !== item.id || cartItem.size !== size)
        })
    }

    function getItemQuantity(item: Item, size: string){
        return cartItems.find(cartItem => cartItem.item.id === item.id && cartItem.size === size)?.quantity || 0
    }
    return <ShoppingCartContext.Provider
    value = {{  cartItems,
                getItemQuantity,
                increaseItemQuantity,
                decreaseItemQuantity,
                removeFromCart}}>
        {children}
    </ShoppingCartContext.Provider>
}