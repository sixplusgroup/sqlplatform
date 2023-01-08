<template>
  <div style="text-align: center;width: 80vw;">
    <div
      class="questionList"
    >

      <div class="selector" >

        <a-button shape="round" class="selectController"
                  v-if="filter" @click="filter = false"
        >收起筛选列表</a-button>
        <a-button shape="round" class="selectController"
                  v-else @click="filter = true"
        >按知识点筛选</a-button>


        <a-button shape="round" class="selectController"
                  style="margin-left: 8px"
                  v-if="filter" @click="resetFilter"
        > <a-icon type="reload" /> 重置</a-button>

        <div v-for="(item,index) in selector" v-if="filter"
             style="display: inline-block;float: left;"
        >
          <a-button shape="round"
                    v-if="item.selected"
                    style="background-color: rgb(120, 135, 185);
                    border: 0;color: white;margin: 0 0 5px 8px"
                    @click="selectFilter(index)"
          >{{ item.value }}
          </a-button>
          <a-button type="link" shape="round"
                    style="color: #333333;border: 0;margin: 0 0 5px 8px"
                    @click="selectFilter(index)"
                    id="unSelected"
                    v-else>{{ item.value }}
          </a-button>
        </div>
<!--        </transition>-->
      </div >
      <a-table :dataSource="questionList" :columns="columns"
               :key="questionList.id"
               :pagination="false"
               :loading="loading"
               class="mainTable"
      >

        <span slot="titles" slot-scope="text, record">
          <a-popover
            trigger="hover"
            :overlayStyle="{maxWidth:'25vw'}"
            placement="left">
            <template slot="content">
              <a-tag v-for="(tag,index) in record.tags"
                     color="rgb(120, 135, 185)"
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

<!--        <span slot="tags" slot-scope="text, record">-->
<!--          <a-tag v-for="(tag,index) in text"-->
<!--                 color="rgb(120, 135, 185)"-->
<!--                 :key="tag"-->
<!--                 style="margin-right: 3px">-->
<!--              {{ tag }}-->
<!--              </a-tag>-->
<!--        </span>-->
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
      // colors:["rgb(111,176,224)", "rgb(154,229,193)",
      //   "rgb(160,166,227)", "rgb(240,205,52)",
      //   "rgb(221,174,216)","rgb(194,123,123)",
      //   "rgb(184,194,68)", "rgb(208,124,48)", "rgb(241,231,200)"],
      selector: [
        {selected: false, value: '时间和日期'},
        {selected: false, value: '字符串'},
        {selected: false, value: '数值'},
        {selected: false, value: '集合'},
        {selected: false, value: '聚合函数'},
        {selected: false, value: '模糊查询'},
        {selected: false, value: '排序'},
        {selected: false, value: '分组'},
        {selected: false, value: '多表连接'},
        {selected: false, value: '子查询'},
        {selected: false, value: '条件判断'},
      ],
      columns: [
        {
          title: '题目名称',
          dataIndex: 'title',
          key: 'title',
          align: 'center',
          // width: 250,
          scopedSlots: {customRender: 'titles'}
        },
        // {
        //   title: '知识点',
        //   dataIndex: 'tags',
        //   key: 'tags',
        //   align: 'center',
        //   scopedSlots: {customRender: 'tags'},
        //   // width: 0,
        //   // filters: [
        //   //   { text: '时间和日期', value: '时间和日期' },
        //   //   { text: '字符串', value: '字符串' },
        //   //   { text: '数值', value: '数值' },
        //   //   { text: '集合', value: '集合' },
        //   //   { text: '聚合函数', value: '聚合函数' },
        //   //   { text: '模糊查询', value: '模糊查询' },
        //   //   { text: '排序', value: '排序' },
        //   //   { text: '分组', value: '分组' },
        //   //   { text: '多表连接', value: '多表连接' },
        //   //   { text: '子查询', value: '子查询' },
        //   //   { text: '条件判断', value: '条件判断' },
        //   // ],
        //   // onFilter: (value, record) => record.tags.indexOf(value) !== -1,
        // },

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
    await this.getQuestionList({userId: this.userId, page: 1, pageSize: 9});
    this.clear_draft()
  },
  computed: {
    ...mapGetters([
      'questionList', 'userId', 'totalMainQuestionNum'
    ])
  },
  methods: {
    ...mapActions([
      'logout', 'getQuestionListByTags',
      'getQuestionList', 'getQuestion', 'getUserInfoByToken'
    ]),
    ...mapMutations([
      'clear_draft'
    ]),
    pageChange(page) {
      this.loading = true
      this.current = page
      let tags = [];
      for(let i in this.selector){
        if(this.selector[i].selected) tags.push(this.selector[i].value);
      }
      const params = {
        userId: this.userId,
        pageSize: 9,
        page: page,
        tags: tags
      }
      if(tags.length!== 0){
        this.getQuestionListByTags(params);
      }else{
        this.getQuestionList(params);
      }
      this.loading = false;
    },
    selectFilter(index) {
      this.selector[index].selected = !this.selector[index].selected;
      let tags = [];
      for(let i in this.selector){
        if(this.selector[i].selected) tags.push(this.selector[i].value);
      }
      let queryParam = {
        userId: this.userId,
        page: this.current,
        pageSize: 9,
        tags: tags
      }
      if(tags.length!== 0){
        this.getQuestionListByTags(queryParam);
      }else{
        this.getQuestionList(queryParam);
      }
    },
    resetFilter(){
      this.selector = [
        {selected: false, value: '时间和日期'},
        {selected: false, value: '字符串'},
        {selected: false, value: '数值'},
        {selected: false, value: '集合'},
        {selected: false, value: '聚合函数'},
        {selected: false, value: '模糊查询'},
        {selected: false, value: '排序'},
        {selected: false, value: '分组'},
        {selected: false, value: '多表连接'},
        {selected: false, value: '子查询'},
        {selected: false, value: '条件判断'},
      ];
      let queryParam = {
        userId: this.userId,
        page: 1,
        pageSize: 9
      }
      this.current = 1;
      this.getQuestionList(queryParam);
    },
    getQuestionDetail(id) {
      this.$router.push({name: 'question', params: {mainId: id}})
    }
  }
}
</script>

<style scoped>
.questionList {
  padding: 3em 0 1em 0;
  text-align: center;
}

.mainTable {
  /*color: ;*/
  margin-bottom: 1em;
  display: inline-block;
  width: 60vw;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
}
.selectController{
  display: inline-block;
  float: left;
  background-color: rgb(120, 135, 185);
  border: 0;color: white
}

.selector {
  width: 60vw;
  display: inline-block;
  margin-bottom: 1em;
}

#unSelected:hover {
  background-color: rgba(174, 184, 221, 0.5);
}

/* 进入动画 */
.v-enter-active {
  animation: move 1s;
}

/* 离开动画 */
.v-leave-active {
  animation: move 1s reverse;
}

</style>
