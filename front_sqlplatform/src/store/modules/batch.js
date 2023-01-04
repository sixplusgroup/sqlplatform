import Vue from 'vue'
import router from '@/router'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  runBatchAPI, commitAPI
} from "../../api/batch";

const batch = {
  state: {
  },
  mutations: {
  },
  actions: {
    runTest: async ({commit, dispatch}, data) => {
      const res = await runBatchAPI(data);
      if (res.msg === 'Passed') {
        message.success('Passed!')

      } else if (res.msg === 'Didn\'t pass') {
        message.warning('Didn\'t pass, please check!')
      }
    },
    commit: async ({commit, dispatch}, data) => {
      const res = await commitAPI(data);
      if (res.msg === 'Passed') {
        message.success('Passed!')
        commit('set_commit_state', {i: data.idx, state: true})
      } else if (res.msg === 'Didn\'t pass') {
        message.warning('Didn\'t pass, please check!')
      }
    },
  }
}

export default batch
