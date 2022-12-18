import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  getMainQuestionAPI, getSubQuestionsAPI,
  getQuestionListAPI, saveDraftAPI,
  getDraftAPI, getStarStateAPI,
  starSubQuestionAPI, unStarSubQuestionAPI
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
      state.mainQuestion = "## " + data.title + "\n" + data.description;
    },
    set_subQuestions: (state, data) => {
      state.subQuestions = data;
    },
    set_questionList: (state, data) => {
      state.questionList = data;
    },
    set_draft: (state, data) => {
      state.draft.push(data);
    },
    clear_draft: (state) => {
      state.draft = [];
    },
    set_star_state: (state, data) => {
      state.subQuestions[data.i].isStared = data.state;
    },
  },
  actions: {
    getQuestion: async ({commit, dispatch}, data) => {
      const res1 = await getMainQuestionAPI(data)
      const res2 = await getSubQuestionsAPI(data)

      if (res1) {
        commit('set_mainQuestion', res1.obj)
        commit('set_subQuestions', res2.obj)
      }
    },
    getQuestionList: async ({commit}, queryParam) => {
      const res = await getQuestionListAPI(queryParam)
      if (res) {
        commit('set_questionList', res.obj)
      }
    },
    saveDraft: async ({commit}, data) => {
      const res = await saveDraftAPI(data)
      if (res) {
        message.success('草稿保存成功')
      }
    },
    getDraft: async ({commit}, data) => {
      const res = await getDraftAPI(data)
      // console.log(res)
      if (res.msg === '未保存过草稿') {
        commit('set_draft', 'SELECT * FROM')
      } else {
        commit('set_draft', res.obj.draft)
      }
    },
    getStarState: async ({commit}, data) => {
      const res = await getStarStateAPI(data)
      if (res.msg === '已收藏') {
        commit('set_star_state', {state: true, i: data.idx})
      } else {
        commit('set_star_state', {state: false, i: data.idx})
      }
    },
    starSubQuestion: async ({commit}, data) => {
      const res = await starSubQuestionAPI(data)
      if (res) {
        commit('set_star_state', {state: true, i: data.idx})
        message.success(res.msg)
      }
    },
    unStarSubQuestion: async ({commit}, data) => {
      const res = await unStarSubQuestionAPI(data)
      if (res) {
        commit('set_star_state', {state: false, i: data.idx})
        message.success(res.msg+'成功')
      }
    },

  }
}

export default question
