import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {getMainQuestionAPI,getSubQuestionsAPI,
  getQuestionListAPI,saveDraftAPI,getDraftAPI
} from "../../api/question";

const question = {
  state: {
    mainQuestion: '',
    subQuestions: {},
    questionList: {},
    draft: [],
  },
  mutations: {
    set_mainQuestion: (state, data) => {
      state.mainQuestion = "## "+data.title+"\n"+data.description;
    },
    set_subQuestions: (state, data) => {
      state.subQuestions = data;
      // console.log(data)
    },
    set_questionList: (state, data) => {
      state.questionList = data;
    },
    set_draft: (state, data) => {
      state.draft.push(data);
    },
    clear_draft: (state) => {
      state.draft=[];
    },
  },
  actions: {
    getQuestion: async ({commit, dispatch}, data) => {
      const res1 = await getMainQuestionAPI(data)
      const res2 = await getSubQuestionsAPI(data)

      if (res1) {
        commit('set_mainQuestion', res1.obj)
        commit('set_subQuestions',res2.obj)
      }
    },
    getQuestionList: async ({commit}, queryParam) => {
      const res = await getQuestionListAPI(queryParam)
      if (res) {
        commit('set_questionList', res.obj)
      }
    },
    saveDraft: async ({commit}, data) => {
      console.log(data)
      const res = await saveDraftAPI(data)
      if (res) {
        message.success('草稿保存成功')
      }
    },
    getDraft: async ({commit}, data) => {
      const res = await getDraftAPI(data)
      if (res.msg === '未保存过草稿') {
        commit('set_draft','SELECT * FROM')
      }else{
        commit('set_draft',res.obj.draft)
      }
    },

  }
}

export default question
