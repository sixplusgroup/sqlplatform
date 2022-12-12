import {axios} from '@/utils/request'
import Qs from 'qs';

export function getMainQuestionAPI(mainId) {
  return axios({
    url: `/api/question/getMainQuestion/${mainId}`,
    method: 'GET',
  }).catch((r) => {
    console.log("catch: ", r)
  })
}

export function getSubQuestionsAPI(mainId) {
  return axios({
    url: `/api/question/getSubQuestion/${mainId}`,
    method: 'GET',
  }).catch((r) => {
    console.log("catch: ", r)
  })
}

//获取题目列表
export function getQuestionListAPI(queryParam) {
  console.log(queryParam)
  return axios({
    url: `/api/question/get_main_question_by_page`,
    method: 'GET',
    params: {
      page: queryParam.page,
      pageSize: queryParam.pageSize
    }
  }).catch((r) => {
    console.log("catch: ", r)
  })
}

export function saveDraftAPI(data) {
  return axios({
    url: `/api/question/save_draft`,
    method: 'POST',
    data
  }).catch((r) => {
    console.log("catch: ", r)
  })
}

export function getDraftAPI(data) {
  return axios({
    url: `/api/question/get_draft`,
    method: 'GET',
    params: {
      userId: data.userId,
      mainId: data.mainId,
      subId: data.subId
    }
  }).catch((r) => {
    console.log("catch: ", r)
  })
}



