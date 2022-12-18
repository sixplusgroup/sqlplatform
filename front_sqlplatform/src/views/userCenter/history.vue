<template>
  <div class="box">
    <div id="chart">

    </div>
    <div id="info_1"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import {mapActions, mapGetters} from "vuex";

export default {
  name: "history",
  async mounted() {
    if (this.userId === '') await this.getUserInfoByToken();
    await this.getUserStatistic(this.userId)
    this.drawEcharts()
  },
  computed: {
    ...mapGetters([
      'userId',
      'userInfo',
      'statistic'
    ]),
  },
  methods: {
    ...mapActions([
      'getUserInfoByToken', 'getUserStatistic'
    ]),
    drawEcharts() {
      var chartDom = document.getElementById('chart');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title:{
          text: this.statistic.passRate.replace('NaN','0'),
          subtext: '提交通过率',
          left: "center",
          top: '40%',
          textStyle: {
            fontSize: 30
          },
          subtextStyle: {
            fontSize: 20
          }
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          bottom: '5%',
          left: 'center'
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
              {value: this.statistic.notStartedQuestionNum, name: '未开始'},
              {value: this.statistic.passedQuestionNum, name: '已通过'},
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
.box{
  display: flex;
  justify-content: space-around;
  width: 80vw;
  padding: 1em;
}
#chart {
  width: 30vw;
  height: 40vh;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
  /*display: inline-block;*/
  text-align: center;

}
#info_1{
  width: 45vw;
  height: 90vh;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
  /*display: inline-block;*/
}

</style>
