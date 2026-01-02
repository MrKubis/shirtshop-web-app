import { useEffect, useState } from "react";
import { useParams } from "react-router";
import api from "../api/api";
import { ItemImage } from "../models/Item";
import { UUID } from "crypto";

export default function ShirtPage(){
    const id = useParams().id as UUID;
    const [images,setImages] = useState<ItemImage | null > (null);
    
    //get item info

    //get item images
    useEffect(()=>{
        fetchImages(id);
    },[])

    function fetchImages( itemId: UUID){
        api.get(`/images/item/${itemId}`)
        .then(response =>{
         setImages(response.data);    
        })
    }


    return(
        <>
        <p></p>
        </>
    );
}