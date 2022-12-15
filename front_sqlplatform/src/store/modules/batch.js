import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  runBatchAPI, commitAPI
} from "../../api/batch";

const batch = {
  state: {
    // passed: false,

  },
  mutations: {
    // set_mainQuestion: (state, data) => {
    //   state.mainQuestion = "## "+data.title+"\n"+data.description;
    // },
    // set_subQuestions: (state, data) => {
    //   state.subQuestions = data;
    // },
    // set_passed: (state, data) => {
    //   state.passed = data;
    // },
  },
  actions: {
    runTest: async ({commit}, data) => {
      const res = await runBatchAPI(data);
      // const res2 = await getBatchAPI(res1.msg)
      console.log(res)
      // console.log(res2)
      if (res.msg === 'Passed') {
        message.success('Passed!')
      }else if(res.msg === 'Didn\'t pass'){
        message.warning('Didn\'t pass, please check!')
      }
    },
    commit: async ({commit}, data) => {
      const res = await commitAPI(data);
      console.log(res)
      // if (res.msg === 'Passed') {
      //   message.success('Passed!')
      // }else if(res.msg === 'Didn\'t pass'){
      //   message.warning('Didn\'t pass, please check!')
      // }
    }



  }
}

export default batch
