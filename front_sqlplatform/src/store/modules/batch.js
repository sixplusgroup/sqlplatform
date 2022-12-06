import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  createBatchAPI,getBatchAPI
} from "../../api/batch";

const batch = {
  state: {

  },
  mutations: {
    // set_mainQuestion: (state, data) => {
    //   state.mainQuestion = "## "+data.title+"\n"+data.description;
    // },
    // set_subQuestions: (state, data) => {
    //   state.subQuestions = data;
    // },
    // set_questionList: (state, data) => {
    //   state.questionList = data;
    // },
  },
  actions: {
    runTest: async ({commit}, data) => {
      const res1 = await createBatchAPI(data);
      // const res2 = await getBatchAPI(res1.msg)
      console.log(res1)
      // console.log(res2)
      // if (res1) {
      //   // commit('set_mainQuestion', res1.obj)
      // }
    },


  }
}

export default batch
