<template>
  <div class="box">
    <div class="chartBox">
      <div id="pieChart" class="chart"></div>
      <div id="radarChart" class="chart"></div>
    </div>
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
        <template #title style="float: left;margin-left: 1em">
          <h4>
            最近提交记录（近20条）
          </h4>
        </template>
<!--        <span slot="tags" style="display: none"></span>-->

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
        // {
        //   title: '',
        //   dataIndex: 'tags',
        //   key: 'tags',
        //   align: 'center',
        //   scopedSlots: {customRender: 'tags'},
        //   width: 0,
        //
        // },
        {
          title: '提交时间',
          dataIndex: 'latestSubmitTime',
          key: 'latestSubmitTime',
          align: 'center',
          scopedSlots: {customRender: 'latestSubmitTime'},
          width: '18%'
        },
        {
          title: '题目名称',
          dataIndex: 'title',
          key: 'title',
          align: 'center',
          width: '12%',
          filters: [
            { text: "时间和日期", value: '时间和日期' },
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
          title: '小题描述',
          dataIndex: 'description',
          key: 'description',
          align: 'center',
          width: '35%',
          ellipsis: true,
        },
        {
          title: '难度',
          dataIndex: 'difficulty',
          key: 'difficulty',
          align: 'center',
          width: 50,
          scopedSlots: {customRender: 'difficulty'}
        },
        {
          title: '提交次数',
          dataIndex: 'submitTimes',
          key: 'submitTimes',
          align: 'center',
          width: 100,
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 100,
          scopedSlots: {customRender: 'action'}
        },
      ]
    }
  },

  async mounted() {
    if (this.userId === '') await this.getUserInfoByToken();
    await this.getUserStatistic(this.userId)
    this.drawPieEcharts()
    this.drawRadarChart()
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
    drawPieEcharts() {
      let pieChart = document.getElementById('pieChart');
      let myChart = echarts.init(pieChart);
      let option = {
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
    },
    drawRadarChart(){
      let radarChart = document.getElementById('radarChart');
      let myChart = echarts.init(radarChart);
      let tagData = this.statistic.tagRadar;
      // console.log(tagData)
      let option ={
        title: {
          text: '完成进度',
          left: '15%',
          top: '43%'
        },
        legend: {
          data: ['Allocated Budget', 'Actual Spending']
        },
        radar: {
          shape: 'circle',
          radius: 70,
          indicator: [
            { name: '时间和日期', max: tagData[0].时间和日期.totalNum },
            { name: '字符串', max: tagData[1].字符串.totlNum },
            { name: '数值', max: tagData[2].数值.totalNum },
            { name: '集合', max: tagData[3].集合.totalNum },
            { name: '聚合函数', max: tagData[4].聚合函数.totalNum },
            { name: '模糊查询', max: tagData[5].模糊查询.totalNum },
            { name: '排序', max: tagData[6].排序.totalNum },
            { name: '分组', max: tagData[7].分组.totalNum },
            { name: '多表连接', max: tagData[8].多表连接.totalNum },
            { name: '子查询', max: tagData[9].子查询.totalNum },
            { name: '条件判断', max: tagData[10].条件判断.totalNum },
          ],
          splitArea: {
            // show: false,
            areaStyle:{color: ['rgba(102,202,238,0.2)','rgba(200,207,208,0.2)']}
          },
          splitNumber: 3,
          axisLine: {
            lineStyle: {
              color: 'rgba(102,202,238,0.33)'
            }
          },
          axisName: {
            color: 'gray'
          },
        },
        series: [
          {
            // name: 'Budget vs spending',
            type: 'radar',
            symbol: 'none',
            data: [
              {
                value: [tagData[0].时间和日期.passedNum,
                  tagData[1].字符串.passedNum,
                  tagData[2].数值.passedNum,
                  tagData[3].集合.passedNum,
                  tagData[4].聚合函数.passedNum,
                  tagData[5].模糊查询.passedNum,
                  tagData[6].排序.passedNum,
                  tagData[7].分组.passedNum,
                  tagData[8].多表连接.passedNum,
                  tagData[9].子查询.passedNum,
                  tagData[10].条件判断.passedNum],
                name: '完成度',
                lineStyle: {
                  width: 1,
                  opacity: 0.5
                },
                areaStyle: {
                  color: 'rgba(115,185,252,0.7)'
                }
              }
            ]
          }
        ]
      };
      console.log(option.series[0].data)
      option && myChart.setOption(option);
    }
  }
}
</script>

<style scoped>
.box {

  /*justify-content: space-around;*/
  width: 80vw;
  padding: 1em;
  height: 100vh;
  overflow: auto;
}

.chartBox{
  display: flex;
}

.chart {
  width: 40vw;
  height: 30vh;
  display: inline-block;
  /*box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);*/
  /*display: inline-block;*/
  text-align: center;
}
/*#radarChart{*/
/*  margin: 2em;*/
/*}*/

#info_1 {
  width: 76vw;
  /*height: 90vh;*/
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
  display: inline-block;
  /*display: inline-block;*/
}

</style>
