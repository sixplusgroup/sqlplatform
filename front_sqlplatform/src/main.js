// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import 'ant-design-vue/dist/antd.css';
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.less'

Vue.config.productionTip = false

import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// highlightjs
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
  Hljs: hljs,
});
Vue.use(VMdPreview);
Vue.use(Antd);

router.beforeEach((to, from, next) => {
  //获取用户登录成功后储存的登录标志
  let getFlag = localStorage.getItem("Flag");
  //如果登录标志存在且为isLogin，即用户已登录
  if(getFlag === "isLogin"){
    //设置vuex登录状态为已登录
    store.state.isLogin = true;
    next();
    //如果已登录，还想想进入登录注册界面，则定向回首页
    if (!to.meta.isLogin) {
      next({
        path: '/homePage/exerciseList'
      })
    }
    //如果登录标志不存在，即未登录
  }else{
    //用户想进入需要登录的页面，则定向回登录界面
    if(to.meta.isLogin){
      next({
        path: '/login',
      })
    }else{
      next()
    }
  }
});


router.afterEach(route => {
  window.scroll(0, 0);
});

/* eslint-disable no-new */
new Vue({
  router,//引入router
  store,//引vuex
  render: h => h(App)
}).$mount('#app');
