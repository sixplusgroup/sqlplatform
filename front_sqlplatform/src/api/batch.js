import { axios } from '@/utils/request'
import Qs from 'qs';

export function runBatchAPI(data){
  console.log(data)
  return axios({
    url:`/api/batch/run`,
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
