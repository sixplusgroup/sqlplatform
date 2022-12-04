<template>
  <div>
<p>Developing...</p>
    <a-button @click="getQuestions">随机一题（题目详情页面）</a-button>
  <a-button @click="handleLogout">Logout</a-button>
  </div>
</template>

<script>
import {mapGetters, mapActions,mapMutations} from 'vuex'

export default {
  name: "exerciseList",
  methods:{
    ...mapActions([
      'logout',
      'getQuestion'
    ]),
    handleLogout(){
      this.logout()
    },
    getQuestions(){
      let mainId = Math.floor(Math.random()*20);
      this.$router.push({ name: 'question', params: { mainId: (mainId) }})
    }
  }
}
</script>

<style scoped>

</style>
