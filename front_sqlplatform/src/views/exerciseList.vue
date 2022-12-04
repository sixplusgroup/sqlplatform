<template>
  <div style="text-align: center;">
    <div
      class="questionList"
    >
      <a-table :dataSource="questionList" :columns="columns"
               bordered
               :pagination="false"
               :loading="loading"
               style="margin-bottom: 1em"
      >
        <span slot="action" slot-scope="text, record">
      <a @click="getQuestionDetail(record.id)">去做题</a>
      <a-divider type="vertical"/><a>收藏(未实现)</a>
    </span>


      </a-table>
      <a-pagination v-model:current="current"
                    @change="pageChange"
                    :total="20"
                    show-less-items></a-pagination>
    </div>


    <a-button @click="getRandomQuestions">随机一题</a-button>
    <a-button @click="handleLogout">Logout</a-button>
  </div>
</template>

<script>
import {mapGetters, mapActions, mapMutations} from 'vuex'
import { ref } from 'vue';
export default {
  name: "exerciseList",
  data() {
    return {
      // dataSource: {},
      current: ref(1),
      loading: false,
      columns: [
        {
          title: '题目编号',
          dataIndex: 'id',
          key: 'id',
        }, ,
        {
          title: '题目名称',
          dataIndex: 'title',
          key: 'title',
        },
        {
          title: '难度等级',
          dataIndex: 'difficulty',
          key: 'difficulty',
        },
        {
          title: '小题数目',
          dataIndex: 'subCount',
          key: 'subCount',
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: { customRender: 'action' }
        },
      ],
    }
  },
  mounted() {
    this.getQuestionList({page: 1, pageSize: 10})
    // this.dataSource = this.questionList

  },
  computed: {
    ...mapGetters([
      'questionList'
    ])
  },
  methods: {
    ...mapActions([
      'logout',
      'getQuestionList', 'getQuestion'
    ]),
    pageChange(page) {
      this.loading = true
      this.current = page
      const params = {
        pageSize: 10,
        page: page,
      }
      this.getQuestionList(params) //获取列表数据
      this.loading = false;
    },
    handleLogout() {
      this.logout()
    },
    getRandomQuestions() {
      let mainId = Math.floor(Math.random() * 20);
      this.$router.push({name: 'question', params: {mainId: (mainId)}})
    },
    getQuestionDetail(id) {
      this.$router.push({name: 'question', params: {mainId: id}})
    }
  }
}
</script>

<style scoped>
.questionList {
  padding: 2em 10em 1em 10em;
  text-align: center;
}

</style>
