import Vue from 'vue'
import router from '@/router'
import {getToken, setToken, removeToken, setSecretToken, getSecretToken, removeSecretToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'

import {
  loginAPI,
  sendCodeAPI,
  registerAPI
} from '../../api/user'

const user = {
  state: {
    userId: '',
    token: '',
    userInfo: {}
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

  },
  actions: {
    login: async ({dispatch, commit}, userData) => {
      const res = await loginAPI(userData)
      console.log(res)
      if (res) {
        setToken(res.id)
        // setSecretToken(res.token)
        localStorage.setItem("token", res.token)
        commit('set_userId', res.id)
        // dispatch('getUserInfo')
        router.push('/')
        message.success("登录成功")
      } else {
        message.error('用户名或密码错误！')
      }
    },
    register: async ({commit}, data) => {
      const res = await registerAPI(data)
      if (res) {
        message.success('注册成功')
      }
    },
    sendCode: async ({commit}, email) => {
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
  }
}

export default user
