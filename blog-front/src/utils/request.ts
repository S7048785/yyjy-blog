import axios from "axios"
import router from "@/router";
const apiUrl = import.meta.env.VITE_APP_API_URL
const request = axios.create({
  baseURL: apiUrl,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  }
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    return config;
  },
  error => {

    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    if (response.data.code === 0) {

      router.push('/404')
    }
    return response.data
  },
  error => {
    return Promise.reject(error)
  }
)

export default request;
