import { useEffect, useState } from "react";
import { useParams } from "react-router";
import api from "../api/api";
import { Item, ItemImage } from "../models/Item";
import { UUID } from "crypto";
import { LineWobble } from "ldrs/react";
import 'ldrs/react/LineWobble.css'

export default function ShirtPage(){
    const id = useParams().id as UUID;
    const [images,setImages] = useState<Array<ItemImage> | null > (null);
    const [item, setItem] = useState<Item | null> (null);
    const [isLoading, setLoading] = useState<boolean> (true);

    //get item info
    useEffect(()=>{
        api.get(`/items/id/${id}`)
        .then(response =>{
            setItem(response.data);
        })
        .finally(()=>{
            setLoading(false);
        })
    },[])

    //get item images
    useEffect(()=>{
    function fetchImages( itemId: UUID){
        api.get(`/images/item/${itemId}`)
        .then(response =>{
         setImages(response.data);    
        })
    }
    fetchImages(id);
    },[])

    useEffect(()=>{
        console.log(images);
    },[images])


    if(isLoading) return  <div>
                                <LineWobble
                                    size="100"
                                    stroke="8"
                                    bgOpacity="0.1"
                                    speed="1.75"
                                    color="black" 
                                />
                                <p>Loading...</p>
                            </div>
    return(
        <>
        <p></p>
        </>
    );
}