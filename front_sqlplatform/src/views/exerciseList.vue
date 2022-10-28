<template>
  <div>
<p>Developing...</p>
  <a-button @click="handleLogout">Logout</a-button>
  </div>
</template>

<script>
import {mapGetters, mapActions,mapMutations} from 'vuex'

export default {
  name: "exerciseList",
  methods:{
    ...mapActions([
      'logout'
    ]),
    handleLogout(){
      this.logout()

    }
  }
}
</script>

<style scoped>

</style>
