<template>
<div style="width: 80vw;">
  <div class="cardsWrapper">

    <a-row :gutter="18">
      <a-col :span="8" v-for="item in userStars" :key="item.subId">
        <a-card
                :title="item.title" class="starCards">
          <template #extra><a @click="getQuestionDetail(item.mainId)">去做题</a></template>
          <p style="max-height: 105px;overflow: scroll">{{item.description}}</p>
        </a-card>
      </a-col>
    </a-row>

  </div>
</div>

</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "favourites",

  computed: {
    ...mapGetters([
      'userStars',

    ]),

  },
  mounted() {
    this.getUserStars(localStorage.getItem('userId'))

  },
  methods: {
    ...mapActions([
      'getUserStars',
    ]),
    getQuestionDetail(id) {
      this.$router.push({name: 'question', params: {mainId: id}})
    }
  }
}
</script>

<style scoped>
.cardsWrapper{
  padding: 1em;
  display: inline-block;
  margin: 2vh;
  overflow: scroll;
  height: 95vh;
  width: 60vw;
  text-align: center;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
}
.starCards{
  display: inline-block;
  width: 16vw;
  text-align: left;
  margin: 1em 0 1em 0;
}

</style>
