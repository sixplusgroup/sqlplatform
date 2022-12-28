<template>
  <div id="app">
    <transition name="fade-transform" mode="out-in">
      <router-view/>
    </transition>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: 'App',
  computed: {
    ...mapGetters([
      // 'userInfo',
      'isLogin',
      'userId',
    ]),
  },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /*margin-top: 60px;*/

}
.fade-enter-active {
  transition: all .3s ease;
}
.fade-enter, .fade-leave-to{
  transform: translateX(20px);
  opacity: 0;
}
</style>
