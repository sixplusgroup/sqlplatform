import Vue from 'vue'
import axios from 'axios'
import {VueAxios} from './axios'
import {notification, message} from 'ant-design-vue'
import store from '@/store'
import {getToken} from './auth'
import router from '../router'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.NODE_ENV === 'production' ? '' : 'http://localhost:8080',
  withCredentials: true
})
// console.log(process.env.NODE_ENV)
const err = (error) => {
  if (error.response) {
    const data = error.response.data
    const token = Vue.ls.get('ACCESS_TOKEN')
    if (error.response.status === 403) {
      notification.error({
        message: 'Forbidden',
        description: data.message
      })
    }
    if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
      notification.error({
        message: 'Unauthorized',
        description: 'Authorization verification failed'
      })
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    }
  }
  return Promise.reject(error)
}

//request incerceptor
service.interceptors.request.use(
  (config) => {
    const requestConfig = {
      ...config,
      url: `${config.url}`,
    }
    if (localStorage.getItem("token")) {
      const token = localStorage.getItem("token");
      console.log("TokenFromLocalStore ", token)
      requestConfig.headers.token = token;
    }
    return requestConfig
  }, err)

service.interceptors.response.use((response) => {
  // console.log(response)
  switch (response.status) {
    case 200:
      if (response.data.res === 'success') {
        return response.data
      }
      if (response.data.res === 'failure') {
        return response.data
      }
      message.error(response.data.msg)
      break
    case 404:
      return false
    case 401:
      // 返回 401 清除token信息并跳转到登录页面
      // store.commit('logout');
      router.replace({
        path: 'login',
        query: {redirect: router.currentRoute.fullPath}
      });
      break;
    default:
      // message.error(response.data.msg)
  }
})

const installer = {
  vm: {},
  install(Vue) {
    Vue.use(VueAxios, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}
