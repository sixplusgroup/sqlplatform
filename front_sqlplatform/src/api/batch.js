import { axios } from '@/utils/request'
import Qs from 'qs';

export function createBatchAPI(data){
  return axios({
    url:`/api/batch/create`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: " , r)
  })
}

export function getBatchAPI(id){
  return axios({
    url:`/api/batch/get`,
    method: 'GET',
    params: id
  }).catch((r) => {
    console.log("catch: " , r)
  })
}
