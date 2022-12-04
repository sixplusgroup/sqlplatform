import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  loginAPI,
  sendCodeAPI,
  registerAPI
} from '../../api/user'

import {getMainQuestionAPI,getSubQuestionsAPI} from "../../api/question";

const question = {
  state: {
    mainQuestion: {},
    subQuestions: {}
  },
  mutations: {
    set_mainQuestion: (state, data) => {
      state.mainQuestion = "## "+data.title+"\n"+data.desc;
    },
  },
  actions: {
    getQuestion: async ({commit}, data) => {
      const res1 = await getMainQuestionAPI(data)
      const res2 = await getSubQuestionsAPI(data)

      if (res1) {
        commit('set_mainQuestion', res1.obj)
      }
    },

  }
}

export default question
