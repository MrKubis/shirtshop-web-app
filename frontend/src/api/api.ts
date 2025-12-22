import axios from "axios";

const api = axios.create({
    baseURL:"http://localhost:5257/api",
    withCredentials:true
})

export default api;