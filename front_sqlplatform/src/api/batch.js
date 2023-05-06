import { axios } from '@/utils/request'
import Qs from 'qs';

export function runBatchAPI(data){
  return axios({
    url:`/api/batch/run`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: " , r)
  })
}
export function commitAPI(data){
  return axios({
    url:`/api/batch/submit`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: " , r)
  })
}

export function getHintsAPI(data){
  return axios({
    url:`http://localhost:8000/api/score/getHints`,
    // url:`/api/score/getHints`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: " , r)
  })
}
