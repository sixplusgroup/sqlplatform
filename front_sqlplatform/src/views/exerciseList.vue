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

        <span slot="titles" slot-scope="text, record">
          <a-popover
            title="知识点"
            trigger="hover"
            :overlayStyle="{width:'25vw'}"
            placement="right">
            <template slot="content">
              <a-tag v-for="(tag,index) in record.tags"
                     color="blue"
                     :key="tag"
                     style="margin: 3px">
              {{ tag }}
              </a-tag>
            </template>
            {{ text }}
          </a-popover>
        </span>

        <span slot="action" slot-scope="text, record">
          <a @click="getQuestionDetail(record.mainId)">
            <a-icon type="edit"/>&nbsp去做题</a>
        </span>

        <span slot="progress" slot-scope="text, record">
          <a-progress
            :percent="(record.passedNum)/record.subCount*100"
            size="small"
          />
        </span>

        <span slot="passedNum" slot-scope="text, record">
          {{ text + ' / ' + record.subCount }}
        </span>

        <span slot="submittedButNotPassNum" slot-scope="text, record">
          {{ text + ' / ' + record.subCount }}

        </span>

        <span slot="tags" style="display: none"></span>
      </a-table>
      <a-pagination v-model:current="current"
                    @change="pageChange"
                    :total="totalMainQuestionNum"
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
      colors:["rgb(111,176,224)", "rgb(154,229,193)",
        "rgb(160,166,227)", "rgb(240,205,52)",
        "rgb(221,174,216)","rgb(194,123,123)",
        "rgb(184,194,68)", "rgb(208,124,48)", "rgb(241,231,200)"],
      columns: [
        // {
        //   title: '',
        //   dataIndex: 'tags',
        //   key: 'tags',
        //   align: 'center',
        //   scopedSlots: {customRender: 'tags'},
        //   width: 0,
        //   filters: [
        //     { text: '时间和日期', value: '时间和日期' },
        //     { text: '字符串', value: '字符串' },
        //     { text: '数值', value: '数值' },
        //     { text: '集合', value: '集合' },
        //     { text: '聚合函数', value: '聚合函数' },
        //     { text: '模糊查询', value: '模糊查询' },
        //     { text: '排序', value: '排序' },
        //     { text: '分组', value: '分组' },
        //     { text: '多表连接', value: '多表连接' },
        //     { text: '子查询', value: '子查询' },
        //     { text: '条件判断', value: '条件判断' },
        //   ],
        //   onFilter: (value, record) => record.tags.indexOf(value) !== -1,
        // },
        {
          title: '题目名称',
          dataIndex: 'title',
          key: 'title',
          align: 'center',
          // width: 250,
          scopedSlots: {customRender: 'titles'}
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
          width: '20%',
          scopedSlots: {customRender: 'submittedButNotPassNum'}
        },
        {
          title: '完成进度',
          dataIndex: 'progress',
          key: 'progress',
          align: 'center',
          // width: '40%',
          scopedSlots: {customRender: 'progress'}
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          scopedSlots: {customRender: 'action'}
        },
      ],
      filter: false,
    }
  },
  async mounted() {
    await this.getUserInfoByToken();
    await this.getQuestionList({userId: this.userId, page: 1, pageSize: 10});
    this.clear_draft()
  },
  computed: {
    ...mapGetters([
      'questionList', 'userId', 'totalMainQuestionNum'
    ])
  },
  methods: {
    ...mapActions([
      'logout','getQuestionListByTags',
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

.mainTable {
  color: ;
  margin-bottom: 1em;
  display: inline-block;
  width: 60vw;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
}

</style>
