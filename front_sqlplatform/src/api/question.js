import { axios } from '@/utils/request'
import Qs from 'qs';

export function getMainQuestionAPI(mainId){
  return axios({
    url:`/api/question/getMainQuestion/${mainId}`,
    method: 'GET',
  }).catch((r) => {
    console.log("catch: " , r)
  })
}
export function getSubQuestionsAPI(mainId){
  return axios({
    url:`/api/question/getSubQuestion/${mainId}`,
    method: 'GET',
  }).catch((r) => {
    console.log("catch: " , r)
  })
}



