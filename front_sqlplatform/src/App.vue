<template>
  <div id="layout">
    <Header class="outHeaderStyle"></Header>
    <!--        引入了一个全局父样式，通过router view进行局部内容切换-->
    <transition name="fade-transform" mode="out-in">
      <router-view/>
    </transition>
    <!--        <footerPage/>-->
  </div>
</template>

<script>
import Header from './components/header.vue';

export default {
  components: { Header },
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.outHeaderStyle{
  position:fixed;
  top:0px;
  width: 100vw;
  z-index:999;
}
.fade-enter-active {
  transition: all .3s ease;
}
.fade-enter, .fade-leave-to{
  transform: translateX(20px);
  opacity: 0;
}
</style>
