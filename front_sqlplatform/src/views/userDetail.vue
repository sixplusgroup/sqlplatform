<template>
  <div class="info-wrapper">
    <div class="leftInfo">
      <img class="userimg"
           :src=" userInfo.pic?'data:image/png;base64,'+userInfo.pic:'https://spring-security.oss-cn-beijing.aliyuncs.com/img/93194072.jpeg'">
      <div class="usernamesty">{{ userInfo.name }}</div>
      <div class="userdescript">{{ "欢迎使用数据库练习平台" }}</div>
      <div class="useriteminfo">
        <a-icon class="itemicon" type="mail"/>
        <div class="itemdisplay"> {{ userInfo.email }}</div>
      </div>
      <a-menu
        style="width: 100%; background-color: transparent;margin-top: 3em"
        default-selected-keys="exerciseList"
        v-model:activeKey="selectedKey"
        mode="inline"
        theme="dark"
        @click="handleSelectMenu"
      >
        <a-menu-item key="exerciseList">
          <span> <a-icon type="unordered-list"/>题目列表 </span>
        </a-menu-item>

        <a-menu-item key="star">
          <span> <a-icon type="star"/>我的收藏夹 </span>
        </a-menu-item>

        <a-menu-item key="record">
          <span> <a-icon type="line-chart"/>我的做题记录 </span>
        </a-menu-item>

        <a-menu-item key="info">
          <span> <a-icon type="user"/>个人信息修改 </span>
        </a-menu-item>


      </a-menu>
      <a-button type="round" ghost
                @click="showAboutUs(e)"
                style=""
                id="about"
      > About Us
      </a-button>

      <a-button type="round" ghost
                @click="this.logout"
                style="margin-top: 3em"
      > Logout
      </a-button>

      <a-modal
        title="关于我们"
        :visible="aboutUsVisible"
        @cancel="closeAboutUs"
        :footer=[]
      >
        <p>若您发现系统存在任何漏洞，或希望提出优化建议，欢迎联系我们：</p>
        <h3>何文兵</h3>
        <p> 邮箱：522022320047@smail.nju.edu.cn</p>
        <p> QQ：826947112</p>
        <h3>王宇博</h3>
        <p> 邮箱：522022320147@smail.nju.edu.cn</p>
        <p> QQ：799580275</p>
        <h3>王馨逸</h3>
        <p> 邮箱：522022320146@smail.nju.edu.cn</p>
        <p> QQ：2671913949</p>
        <h3>沈祎辰</h3>
        <p> 邮箱：522022320117@smail.nju.edu.cn</p>
        <p> QQ：2624020095</p>
      </a-modal>
    </div>
  </div>
</template>
<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'

export default {
  name: 'userDetail',
  data() {
    return {
      loading: false,
      selectedKey: 'exerciseList',
      modify: false,
      data: [],
      form: this.$form.createForm(this, {name: 'coordinated'}),
      aboutUsVisible: false
    }
  },
  computed: {
    ...mapGetters([
      'userInfo',
    ]),
  },
  mounted() {
    this.selectedKey = window.location.hash.split('/')[2];
    this.getUserInfoByToken()
  },
  methods: {
    ...mapActions([
      'getUserInfoByToken', 'logout'
    ]),
    ...mapMutations([]),
    handleSelectMenu(e) {
      this.loading = true;
      this.selectedKey = e.key;
      this.$router.push({name: e.key})
      this.loading = false;
    },
    showAboutUs(e){
      this.aboutUsVisible = true;
    },
    closeAboutUs(){
      this.aboutUsVisible = false;
      // window.blur();
    }
  }
}
</script>
<style scoped>
/deep/ .ant-menu.ant-menu-dark .ant-menu-item-selected{
  background-color: rgb(130, 141, 175);
}
</style>
<style scoped lang="less">
.info-wrapper {
  display: flex;
}

.leftInfo {
  width: 20vw;
  float: left;
  background-image: url('../assets/bg.jpeg');
  background-repeat: round;
  //background-color: rgb(221, 231, 239);
  height: 100vh;
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  color: white;
}

.userimg {
  margin-top: 60px;
  //margin-bottom: 20px;
  width: 104px;
  height: 104px;
  //margin-left: 100px;
  border-radius: 50%;
  margin-bottom: 20px;
}

.usernamesty {
  //color: white;
  font-size: 20px;
  text-align: center;
  line-height: 30px;
  margin-bottom: 10px;
}

.userdescript {
  color: white;
  font-size: 15px;
  text-align: center;
  margin-bottom: 20px;
}

.useriteminfo {
  display: inline-flex;
  //flex-direction: row;
  line-height: 40px;
  //margin-left: 70px;

  .itemicon {
    line-height: 40px;
    padding-top: 2px;
    vertical-align: center;
  }

  .itemdisplay {
    line-height: 40px;
    vertical-align: center;
    margin-left: 10px;
  }
}

#about{
  margin-top: 3em;

}

#about:focus{
  border-color: white;
  color: white;
}

</style>
