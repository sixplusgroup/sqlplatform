import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {getMainQuestionAPI,getSubQuestionsAPI,
  getQuestionListAPI
} from "../../api/question";

const question = {
  state: {
    mainQuestion: '',
    subQuestions: {},
    questionList:{}
  },
  mutations: {
    set_mainQuestion: (state, data) => {
      state.mainQuestion = "## "+data.title+"\n"+data.description;
    },
    set_subQuestions: (state, data) => {
      state.subQuestions = data;
    },
    set_questionList: (state, data) => {
      state.questionList = data;
    },
  },
  actions: {
    getQuestion: async ({commit}, data) => {
      const res1 = await getMainQuestionAPI(data)
      const res2 = await getSubQuestionsAPI(data)

      if (res1) {
        commit('set_mainQuestion', res1.obj)
        commit('set_subQuestions',res2.obj)
      }
    },
    getQuestionList: async ({commit}, queryParam) => {
      const res = await getQuestionListAPI(queryParam)
      // console.log(res)
      if (res) {
        commit('set_questionList', res.obj)
      }
    },

  }
}

export default question
