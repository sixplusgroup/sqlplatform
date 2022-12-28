<template>
    <div id="layout">
      <user-detail></user-detail>
        <transition name="fade-transform" mode="out-in">
            <router-view/>
        </transition>
    </div>
</template>
<script>
import userDetail from '../views/userDetail'
export default {
    name: 'layout',
    components: {
      userDetail,
    },

}
</script>

<style scoped>
#layout{
  display: inline-flex;
}

</style>
