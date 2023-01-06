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

      <span slot="tags" slot-scope="text, record">
        <a-tag v-for="(tag) in text"
               color="blue"
               :key="tag"
               style="margin: 4px">
              {{ tag }}
              </a-tag>
      </span>
      <template #title>
        <h4>收藏夹</h4>
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
          width: '40%',
        },
        {
          title: '知识点',
          dataIndex: 'tags',
          key: 'tags',
          align: 'center',
          scopedSlots: {customRender: 'tags'},
          width: 200,
          filters: [
            { text: '时间和日期', value: '时间和日期' },
            { text: '字符串', value: '字符串' },
            { text: '数值', value: '数值' },
            { text: '集合', value: '集合' },
            { text: '聚合函数', value: '聚合函数' },
            { text: '模糊查询', value: '模糊查询' },
            { text: '排序', value: '排序' },
            { text: '分组', value: '分组' },
            { text: '多表连接', value: '多表连接' },
            { text: '子查询', value: '子查询' },
            { text: '条件判断', value: '条件判断' },
          ],
          onFilter: (value, record) => record.tags.indexOf(value) !== -1,
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
  width: 76vw;
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
