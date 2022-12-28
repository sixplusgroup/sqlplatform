<template>
  <div style="text-align: center;width: 80vw;">
    <div
      class="questionList"
    >
      <a-table :dataSource="questionList" :columns="columns"
               :key="questionList.id"
               :pagination="false"
               :loading="loading"
               class="mainTable"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="getQuestionDetail(record.mainId)">去做题</a>
        </span>

        <span slot="passedNum" slot-scope="text, record">
          {{text+' / '+record.subCount}}
        </span>

        <span slot="submittedButNotPassNum" slot-scope="text, record">
          {{text+' / '+record.subCount}}
        </span>

      </a-table>
      <a-pagination v-model:current="current"
                    @change="pageChange"
                    :total="30"
                    show-less-items></a-pagination>
    </div>
  </div>
</template>

<script>
import {mapGetters, mapActions, mapMutations} from 'vuex'
import {ref} from 'vue';

export default {
  name: "exerciseList",
  data() {
    return {
      current: ref(1),
      loading: false,
      columns: [
        {
          title: '题目名称',
          dataIndex: 'title',
          key: 'title',
          align: 'center',
          width: 250
        },
        {
          title: '已通过',
          dataIndex: 'passedNum',
          key: 'passedNum',
          align: 'center',
          scopedSlots: {customRender: 'passedNum'}
        },
        {
          title: '提交未通过',
          dataIndex: 'submittedButNotPassNum',
          key: 'submittedButNotPassNum',
          align: 'center',
          scopedSlots: {customRender: 'submittedButNotPassNum'}
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          scopedSlots: {customRender: 'action'}
        },
      ],
    }
  },
  async mounted() {
    if (this.userId === '') await this.getUserInfoByToken();
    await this.getQuestionList({userId: this.userId, page: 1, pageSize: 10});
    this.clear_draft()
  },
  computed: {
    ...mapGetters([
      'questionList','userId'
    ])
  },
  methods: {
    ...mapActions([
      'logout',
      'getQuestionList', 'getQuestion', 'getUserInfoByToken'
    ]),
    ...mapMutations([
      'clear_draft'
    ]),
    pageChange(page) {
      this.loading = true
      this.current = page
      const params = {
        userId: this.userId,
        pageSize: 10,
        page: page,
      }
      this.getQuestionList(params) //获取列表数据
      this.loading = false;
    },
    handleLogout() {
      this.logout()
    },
    getQuestionDetail(id) {
      this.$router.push({name: 'question', params: {mainId: id}})
    }
  }
}
</script>

<style scoped>
.questionList {
  padding: 4em 0 1em 0;
  text-align: center;
}
.mainTable{
  margin-bottom: 1em;
  display: inline-block;
  width: 60vw;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
}

</style>
