import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login.vue'

// import Layout from '../views/layout.vue'
import App from "../App";

import exerciseList from '../views/exerciseList.vue'

import userProfile from "../views/userCenter/userProfile";
import favourites from "../views/userCenter/favourites";
import history from "../views/userCenter/history";

import question from '../views/question.vue'

import Qs from 'qs';


Vue.use(VueRouter)

Vue.prototype.Qs = Qs;
const routes = [
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {
        isLogin: false
      }
    },
    {
      path: '/',
      name: 'NJUSE',
      redirect: '/homePage/exerciseList',
      component: () => import('../views/layout'),
      children :[
        {
          path: '/homePage/exerciseList',
          name: 'exerciseList',
          component: exerciseList
        },
        {
          path: '/homePage/info',
          name: 'info',
          component: userProfile
        },
        {
          path: '/homePage/star',
          name: 'star',
          component: favourites
        },
        {
          path: '/homePage/record',
          name: 'record',
          component: history
        },
      ]
    },
    {
      path: '/question/:mainId',
      name: 'question',
      component: question,
    },
  ]


const createRouter = () => new VueRouter({
  // mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes
});

const router = createRouter()

router.beforeEach((to, from, next) => {
  // JWT Token
  // console.log(localStorage)
  if (localStorage.getItem("token")) {
    if (to.fullPath === '/login') {
      next('/')
    }
    else {
      next()
    }
  } else {
    // 无Token
    if (to.fullPath === '/login') {
      next()
    } else {
      next('/login')
    }
  }
});

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher // reset router
}

export default router
