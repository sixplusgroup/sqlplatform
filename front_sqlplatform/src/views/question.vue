<template>
<div class="box" ref="box">
  <div class="left">
    <v-md-preview :text="mainQuestion"></v-md-preview>

  </div>
  <div class="resize" title="收缩侧边栏">
<!--    ⋮-->
  </div>
  <div class="mid">
    <div class="subQuestion">
      <a-tabs v-model:activeKey="activeKey" style="text-align: left">
        <a-tab-pane :key="index"  v-for="(item,index) in subQuestions" :tab="'问题' + (index+1)">
          <div
            style="padding: 1em"
          >{{ item.description }}</div>
          <div style="background-color: rgb(247,247,247);padding: 5px;margin-top: 1em "></div>
          <div class="commonEditor">
            <CommonEditor
              :value="codeSnippets"
              language="sql"
              @input="changeTextarea"
              style="height: 50vh"
            ></CommonEditor>
          </div>

          <div class="footbar">
            <a-button shape="round" @click="runCode(item)"> 运行</a-button>
            <a-button shape="round"> 保存</a-button>
            <a-button shape="round"> 提交</a-button>
          </div>

        </a-tab-pane>
      </a-tabs>
    </div>
<!--    <a-divider></a-divider>-->

  </div>
</div>
</template>

<script>
import {mapGetters, mapActions,mapMutations} from 'vuex'

import CommonEditor from '@/components/CommonEditor.vue'


import { ref } from 'vue';
export default {
  name: "question",
  components: { CommonEditor },
  data() {
    return {
      codeSnippets: 'SELECT * FROM ',
      activeKey: ref(0),
    }
  },
  mounted() {
    this.getQuestion(this.$route.params.mainId)
    this.dragControllerDiv();
  },
  computed: {
    ...mapGetters([
      'mainQuestion','subQuestions','userId','subQuestions'
    ])
  },
  methods: {
    ...mapActions([
      'getQuestion','runTest'
    ]),
    dragControllerDiv () {
      var resize = document.getElementsByClassName('resize');
      var left = document.getElementsByClassName('left');
      var mid = document.getElementsByClassName('mid');
      var box = document.getElementsByClassName('box');
      for (let i = 0; i < resize.length; i++) {
        // 鼠标按下事件
        resize[i].onmousedown = function (e) {
          //颜色改变提醒
          resize[i].style.background = '#a8a5a5';
          var startX = e.clientX;
          resize[i].left = resize[i].offsetLeft;
          // 鼠标拖动事件
          document.onmousemove = function (e) {
            var endX = e.clientX;
            var moveLen = resize[i].left + (endX - startX); // （endx-startx）=移动的距离。resize[i].left+移动的距离=左边区域最后的宽度
            var maxT = box[i].clientWidth - resize[i].offsetWidth; // 容器宽度 - 左边区域的宽度 = 右边区域的宽度

            if (moveLen < 32) moveLen = 32; // 左边区域的最小宽度为32px
            if (moveLen > maxT - 150) moveLen = maxT - 150; //右边区域最小宽度为150px

            resize[i].style.left = moveLen; // 设置左侧区域的宽度

            for (let j = 0; j < left.length; j++) {
              left[j].style.width = moveLen + 'px';
              mid[j].style.width = (box[i].clientWidth - moveLen - 10) + 'px';
            }
          };
          // 鼠标松开事件
          document.onmouseup = function (evt) {
            //颜色恢复
            resize[i].style.background = '#d6d6d6';
            document.onmousemove = null;
            document.onmouseup = null;
            resize[i].releaseCapture && resize[i].releaseCapture(); //当你不在需要继续获得鼠标消息就要应该调用ReleaseCapture()释放掉
          };
          resize[i].setCapture && resize[i].setCapture(); //该函数在属于当前线程的指定窗口里设置鼠标捕获
          return false;
        };
      }
    },
    changeTextarea(val) {
      this.codeSnippets = val
    },
    runCode(item){
      this.runTest({
        batch_text: this.codeSnippets,
        user_id: localStorage.getItem('userId'),
        main_id: item.mainId,
        sub_id: item.id,
        driver: 'mysql'
      })
    }
  }
}
</script>

<style scoped>
/*#question{*/
/*  display: flex;*/
/*}*/

/* 拖拽相关样式 */
/*包围div样式*/
.box {
  width: 100%;
  height: 100%;
  /*margin: 1% 0px;*/
  overflow: hidden;
}
/*左侧div样式*/
.left {
  width: calc(40% - 8px);  /*左侧初始化宽度*/
  height: 100vh;
  /*background: #c74c4c;*/
  float: left;
  text-align: left;
  overflow: scroll;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);

}
/*拖拽区div样式*/
.resize {
  cursor: col-resize;
  float: left;
  position: relative;
  /*top: 45vh;*/
  background-color: #d6d6d6;
  /*border-radius: 5px;*/
  /*margin-top: -10px;*/
  /*top: 2vh;*/
  width: 8px;
  height: 100vh;
  background-size: cover;
  background-position: center;
  /*z-index: 99999;*/
  font-size: 32px;
  /*color: white;*/
}
/*!*拖拽区鼠标悬停样式*!*/
/*.resize:hover {*/
/*  background-color: #868686;*/
/*}*/
/*右侧div'样式*/
.mid {
  float: left;
  width: 60%;   /*右侧初始化宽度*/
  height: 100vh;
  /*background: #226fa1;*/
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
  overflow: scroll;
}
.subQuestion{
  height: 20vh;
  margin: 10px;
}
.commonEditor{
  text-align: left;
}
.footbar{
  background-color: rgb(247,247,247);
  /*height: 2em;*/
  float: bottom;
  padding: 6px;
  text-align: right;
}

</style>

