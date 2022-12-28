import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import question from './modules/question'
import batch from "./modules/batch";

import getters from './getters'


Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    user,
    question,
    batch
  },
  state: {
    isLogin: false,
  },
  mutations: {
    // set_residences: function(state, data) {
    //   state.residences = data
    // }
    //保存登录状态
    userStatus(state, flag) {
      state.isLogin = flag
    },
  },
  actions: {
    //获取登录状态
    userLogin({commit}, flag) {
      commit("userStatus", flag)
    },
  },
  getters
})
