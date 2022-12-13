import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  runBatchAPI, getBatchAPI
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
      const res = await runBatchAPI(data);
      // const res2 = await getBatchAPI(res1.msg)
      console.log(res)
      // console.log(res2)
      if (res) {
        // commit('set_mainQuestion', res1.obj)
        return res;
      }
    },


  }
}

export default batch
