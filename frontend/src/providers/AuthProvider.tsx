import { createContext, PropsWithChildren, useEffect, useLayoutEffect, useState } from "react";
import api from "../api/api";

    type AuthContextType = {
        user: User | null
        setUser: React.Dispatch<React.SetStateAction<User | null>>;
    }

    type User = {
        displayName: string,
        userName: string
    }

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({children} :PropsWithChildren) =>{
    
    const [user, setUser] = useState<User | null>(null);

    //Co refresh strony
    useEffect(()=>{
    api.get<User>("/auth/public-token")
    .then(res => setUser(res.data))
    .catch(()=>setUser(null));
    },[]);

    return(
        <AuthContext.Provider value={{user, setUser}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;
