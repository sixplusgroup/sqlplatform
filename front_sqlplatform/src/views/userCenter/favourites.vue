<template>
<div style="width: 80vw;">
  <div class="cardsWrapper">
    <a-table :dataSource="userStars" :columns="columns"
             :key="userStars.id"
             :pagination="false"
    >

      <span slot="action" slot-scope="text, record">
          <a @click="getQuestionDetail(record)">
            <a-icon type="edit"/>&nbsp去做题</a>
        <a-divider type="vertical"></a-divider>
        <a @click="unStar(record)"><a-icon type="star"/>取消收藏</a>
        </span>
      <template #title>
        <h2 style="float: left;margin-left: 1em">收藏夹</h2>
      </template>
    </a-table>

  </div>
</div>

</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "favourites",
  data(){
    return {
      columns: [
        {
          title: '题目名称',
          dataIndex: 'title',
          key: 'title',
          align: 'center',
          // width: 250
        },
        {
          title: '小题描述',
          dataIndex: 'description',
          key: 'description',
          align: 'center',
          width: '50%',
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          scopedSlots: {customRender: 'action'}
        },
      ]
    }
  },

  computed: {
    ...mapGetters([
      'userStars',
      'userId'

    ]),

  },
  async mounted() {
    await this.getUserInfoByToken()
    await this.getUserStars(this.userId)

  },
  methods: {
    ...mapActions([
      'getUserStars','unStarSubQuestion','getUserInfoByToken'
    ]),
    getQuestionDetail(record) {
      this.$router.push({name: 'question', params: {mainId: record.mainId, subId: record.subId}})
    },
    unStar(item){

      this.unStarSubQuestion({
        userId: this.userId,
        mainId: item.mainId,
        subId: item.subId,
        idx: null
      })
    }
  }
}
</script>

<style scoped>
.cardsWrapper{
  padding: 1em;
  display: inline-block;
  margin: 2vh;
  /*overflow: scroll;*/
  overflow:auto;
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
