<template>
    <div id="layout">
        <Header></Header>
        <transition name="fade-transform" mode="out-in">
            <router-view/>
        </transition>
<!--        <footerPage/>-->
    </div>
</template>
<script>
import Header from '../components/header'
// import footerPage from "../components/footerPage";
export default {
    name: 'layout',
    components: {
        Header,
      // footerPage
    },
    // data() {
    //     return {
    //        // routerpath:this.$route.path,
    //
    //     };
    // },
}
</script>
