<template>

  <div class="cardsWrapper">

    <a-card v-for="item in userStars"
            :key="item.subId"
            :title="item.title" class="starCards">
      <template #extra><a href="#">去做题</a></template>
      <p>{{item.description}}</p>
    </a-card>
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
  }
}
</script>

<style scoped>
.cardsWrapper{
  overflow: scroll;
  height: calc(100vh - 47px);
  width: 80vw;
  text-align: center;
}
.starCards{
  display: inline-block;
  width: 50vw;
  text-align: left;
  margin: 1em;
}

</style>
