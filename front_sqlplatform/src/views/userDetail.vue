<template>
  <div class="info-wrapper">
    <div style="width: 20vw">
      <div class="leftInfo">
        <img class="userimg"
             :src=" userInfo.pic?'data:image/png;base64,'+userInfo.pic:'https://avatars.githubusercontent.com/u/93194072?s=200&v=4'">
        <div class="usernamesty">{{ userInfo.name }}</div>
        <div class="userdescript">{{ "欢迎使用数据库练习平台" }}</div>
        <div class="useriteminfo">
          <a-icon class="itemicon" type="mail"/>
          <div class="itemdisplay"> {{ userInfo.email }}</div>
        </div>
        <a-menu
          style="width: 100%; background-color: transparent;margin-top: 3em"
          default-selected-keys="1"
          mode="inline"
          theme="dark"
          @click="handleSelectMenu"
        >
          <a-menu-item key="1">
            <span> <a-icon type="user"/>个人信息 </span>
          </a-menu-item>

          <a-menu-item key="2">
            <span> <a-icon type="unordered-list"/>我的收藏夹 </span>
          </a-menu-item>

          <a-menu-item key="3">
            <span> <a-icon type="line-chart"/>我的做题记录 </span>

          </a-menu-item>

        </a-menu>
      </div>
    </div>
    <div v-if="!loading">
      <user-profile v-if="selectedKey == 1"></user-profile>
      <favourites v-else-if="selectedKey == 2"></favourites>
      <history v-else-if="selectedKey == 3"></history>
    </div>

    <a-spin v-else></a-spin>
  </div>
</template>
<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'
import userProfile from "./userCenter/userProfile";
import history from "./userCenter/history";
import favourites from "./userCenter/favourites";

export default {
  name: 'userDetail',
  data() {
    return {
      loading: false,
      selectedKey: 1,
      modify: false,
      data: [],
      form: this.$form.createForm(this, {name: 'coordinated'}),
    }
  },
  components: {
    userProfile,
    history,
    favourites
  },
  computed: {
    ...mapGetters([
      'userInfo',
    ]),
  },
  mounted() {
    this.getUserInfoByToken()
  },
  methods: {
    ...mapActions([
      'getUserInfoByToken',
    ]),
    ...mapMutations([]),
    handleSelectMenu(e) {
      this.loading = true;
      this.selectedKey = e.key;
      this.loading = false;
    },

  }
}
</script>
<style scoped lang="less">
.info-wrapper {
  padding-top: 1px;
  display: flex;
}

.leftInfo {
  width: 100%;
  float: left;
  background-image: url('../assets/bg.jpeg');
  background-repeat: round;
  //background-color: rgb(221, 231, 239);
  height: calc(100vh - 47px);
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
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

</style>
