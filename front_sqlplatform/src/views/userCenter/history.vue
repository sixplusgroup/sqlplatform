<template>
  <div class="box">
    <div id="chart"></div>
    <div id="info_1">
      <a-table :dataSource="recentSubmit" :columns="columns"
               :key="recentSubmit.id"
               :pagination="false"
      >
        <span slot="latestSubmitTime" slot-scope="text, record">
          {{ text.replace('T', ' ') }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="getQuestionDetail(record)"><a-icon type="edit"/>&nbsp去做题</a>
        </span>
        <span slot="difficulty" slot-scope="text, record">
          <a-tag
            :key="text"
            :color="(text === 'hard') ? 'volcano' : (text === 'medium') ? 'geekblue' : 'green'"
          >
            {{ text.toUpperCase() }}
          </a-tag>
        </span>
        <template #title>
          <h2 style="float: left;margin-left: 1em">最近提交记录</h2>
        </template>
      </a-table>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import {mapActions, mapGetters} from "vuex";

export default {
  name: "history",
  data() {
    return {
      columns: [
        {
          title: '提交时间',
          dataIndex: 'latestSubmitTime',
          key: 'latestSubmitTime',
          align: 'center',
          scopedSlots: {customRender: 'latestSubmitTime'}

          // width: 250
        },
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
          width: 350,
          // textOverflow: 'ellipsis',
          // whiteSpace: 'nowrap',
          overflow: 'scroll'
        },
        {
          title: '难度',
          dataIndex: 'difficulty',
          key: 'difficulty',
          align: 'center',
          // width: 250,
          scopedSlots: {customRender: 'difficulty'}
        },
        {
          title: '提交次数',
          dataIndex: 'submitTimes',
          key: 'submitTimes',
          align: 'center',
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

  async mounted() {
    if (this.userId === '') await this.getUserInfoByToken();
    await this.getUserStatistic(this.userId)
    this.drawEcharts()
    await this.getRecentSubmit(this.userId);
  },
  computed: {
    ...mapGetters([
      'userId',
      'userInfo',
      'statistic', 'recentSubmit'
    ]),
  },
  methods: {
    ...mapActions([
      'getUserInfoByToken', 'getUserStatistic', 'getRecentSubmit'
    ]),
    getQuestionDetail(record) {
      this.$router.push({name: 'question', params: {mainId: record.mainId, subId: record.subId}})
    },
    drawEcharts() {
      var chartDom = document.getElementById('chart');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: this.statistic.passRate.replace('NaN', '0'),
          subtext: '提交通过率',
          left: "center",
          top: '40%',
          textStyle: {
            fontSize: 25
          },
          subtextStyle: {
            fontSize: 15
          }
        },
        legend: {
          orient: 'vertical',
          left: '70%',
          top: 'center'
        },

        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '做题情况统计',
            type: 'pie',
            radius: ['60%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            // emphasis: {
            //   label: {
            //     show: true,
            //     fontSize: 40,
            //     fontWeight: 'bold'
            //   }
            // },
            labelLine: {
              show: false
            },
            data: [
              {value: this.statistic.notStartedQuestionNum, name: '未开始题目'},
              {value: this.statistic.passedQuestionNum, name: '已通过题目'},
              {value: this.statistic.submittedButNotPassQuestionNum, name: '已提交未通过'},
            ]
          }
        ]
      };
      option && myChart.setOption(option);
    }
  }
}
</script>

<style scoped>
.box {
  /*display: flex;*/
  /*justify-content: space-around;*/
  width: 80vw;
  padding: 1em;
  height: 100vh;
  overflow: auto;
}

#chart {
  width: 40vw;
  height: 30vh;
  display: inline-block;
  /*box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);*/
  /*display: inline-block;*/
  text-align: center;

}

#info_1 {
  width: 76vw;
  /*height: 90vh;*/
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
  display: inline-block;
  /*display: inline-block;*/
}

</style>
