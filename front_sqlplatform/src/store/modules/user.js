import Vue from 'vue'
import router from '@/router'
import {getToken, setToken, removeToken, setSecretToken, getSecretToken, removeSecretToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  loginAPI,
  sendCodeAPI,
  registerAPI,
  getUserInfoAPI, getUserStarsAPI, getUserStatisticAPI, modifyInfoAPI
} from '../../api/user'

const user = {
  state: {
    userId: '',
    token: '',
    userInfo: {
      name: null
    },
    userStars: [],
    statistic: {}
  },
  mutations: {
    reset_state: function (state) {
      state.token = '';
      state.userId = '';
      state.userInfo = {};
    },
    set_userId: (state, data) => {
      state.userId = data
    },
    set_userInfo: (state, data) => {
      state.userInfo = data.userInfo;
      state.userId = data.userId;
    },
    set_user_stars: (state, data) => {
      state.userStars = data;
    },
    set_user_statistic: (state, data) => {
      state.statistic = data;
    },

  },
  actions: {
    getUserInfoByToken: async ({commit}) => {
      let data = localStorage.getItem("userId")
      const res = await getUserInfoAPI(data)
      if (res) {
        commit('set_userInfo', {userInfo: res.obj, userId: data})
      }
    },
    login: async ({dispatch, commit}, userData) => {
      const res = await loginAPI(userData)
      if (res) {
        message.success("登录成功")
        setToken(res.obj.id)
        // setSecretToken(res.token)
        localStorage.setItem("token", res.obj.id)
        localStorage.setItem("userId", res.obj.id);
        commit('set_userId', res.obj.id)
        commit('set_userInfo', res.obj)
        router.push('/')
      } else {
        message.error('用户名或密码错误！')
      }
    },
    register: async (data) => {
      const res = await registerAPI(data)
      if (res) {
        message.success('注册成功')
      }
    },
    sendCode: async (email) => {
      const res = await sendCodeAPI(email)
      if (res) {
        message.success('验证码发送成功，请查收')
      }
    },
    logout: async({ commit }) => {
      removeToken()
      removeSecretToken()
      commit('reset_state')
      resetRouter()
      router.push('/login')
      // const res = await logoutAPI();
    },
    getUserStars: async({ commit }, userId) => {
      const res = await getUserStarsAPI(userId);
      if (res) {
        commit('set_user_stars',res.obj)
      }
    },
    getUserStatistic: async({ commit }, userId) => {
      const res = await getUserStatisticAPI(userId);

      if (res) {
        commit('set_user_statistic',res.obj)
      }
    },
    modifyUserInfo: async({ dispatch }, data) => {
      const res = await modifyInfoAPI(data);
      if (res) {
        message.success("修改成功！")
        dispatch('getUserInfoByToken')
      }
    },
  }
}

export default user
