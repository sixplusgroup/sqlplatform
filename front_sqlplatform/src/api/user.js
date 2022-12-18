import { axios } from '@/utils/request'
import Qs from 'qs';

export function registerAPI(data){
  return axios({
    url:`/api/signup`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: " , r)
  })
}

//发送验证码
export function sendCodeAPI(email){
  return axios({
    url:`/api/signup/sendMessageCode/${email}`,
    method: 'GET',
  }).catch((r) => {
    console.log("catch: " , r)
  })
}

export function loginAPI(data){
  return axios({
    url:`/api/signin`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: " , r)
  })
}
export function getUserInfoAPI(data){
  return axios({
    url:`/api/get_user_info`,
    method: 'GET',
    params:{
      userId: data
    }
  }).catch((r) => {
    console.log("catch: " , r)
  })
}

export function getUserStarsAPI(data){
  return axios({
    url:`/api/user/stars`,
    method: 'GET',
    params:{
      userId: data
    }
  }).catch((r) => {
    console.log("catch: " , r)
  })
}

export function getUserStatisticAPI(data){
  return axios({
    url:`/api/user/statistic`,
    method: 'GET',
    params:{
      userId: data
    }
  }).catch((r) => {
    console.log("catch: " , r)
  })
}


export function modifyInfoAPI(data){
  return axios({
    url:`/api/modify_info`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: " , r)
  })
}





