<template>
  <div>
    <div class="header">
      <div class="mytitle">
        <img class="myLogo" src="../assets/logo.png">
      </div>

      <a-menu
        :default-selected-keys="[$route.path]"
        @click="routeLink"
        mode="horizontal"
        style="background-color: transparent;font-weight: bolder"
        class="menu-top">
        <a-menu-item class="menu-top-item" key="/exerciseList">
          <a-icon type="desktop"/>
          <span>题目列表</span>
        </a-menu-item>
<!--        <a-menu-item class="menu-top-item" key="/recommend">-->
<!--          <a-icon type="search"/>-->
<!--          <span>推荐</span>-->
<!--        </a-menu-item>-->
<!--        <a-menu-item class="menu-top-item" key="/selectCar">-->
<!--          <a-icon type="car"/>-->
<!--          <span>筛选</span>-->
<!--        </a-menu-item>-->

<!--        <a-menu-item class="menu-top-item" key="/question" id="q">-->
<!--          <a-icon type="question-circle"/>-->
<!--          <span>问答</span>-->
<!--        </a-menu-item>-->
<!--        <a-menu-item class="menu-top-item" key="/userPortrait">-->
<!--          <a-icon type="user"/>-->
<!--          <span>偏好</span>-->
<!--        </a-menu-item>-->
      </a-menu>

      <a-dropdown class="userinfo" placement="bottomCenter">
        <div id="user">{{ userInfo.username }}</div>
        <a-menu slot="overlay" @click="onClickUser">
          <a-menu-item key="2">
            退出登录
          </a-menu-item>
        </a-menu>
      </a-dropdown>

    </div>
  </div>
</template>

<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'

export default {
  data() {
    return {}
  },
  computed: {
    ...mapGetters([
      'userInfo',
      'userId',
      // 'chatMessages'
    ]),

  },
  async mounted() {
    // if(this.userId === '') {this.getUserInfo()}
  },
  methods: {
    ...mapActions([
      'logout',
      // 'getUserInfo'
    ]),
    // ...mapMutations([
    //   'set_message',
    // ]),
    routeLink(e) {
      this.$router.push(e.key)
    },
    onClickUser() {
      this.logout();
    }
  }
}


</script>

<style>
.menu-top {
  width: 540px;
  height: inherit;
  display: inline-block;
  margin-left: 2em;
  background-color: transparent;
}

.menu-top-item {
  height: inherit
  /*background-color: #01111f;*/
}

.header {
  /*display: flex;*/
  text-align: left;
  background-color: transparent;
  line-height: 47px;
  height: 47px;
  align-items: center;
  /*justify-content: space-between;*/
  min-width: 100%;
  box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
  /*margin-bottom: 1px;*/
}

.mytitle {
  font-size: 26px;
  margin: 0 0 0 0;
  /*color: #ffffff;*/
  float: left;
}

/*.ant-menu.ant-menu-dark .ant-menu-item-selected {*/
/*  color: #a51f1f;*/
/*}*/
/*#user{*/

/*}*/
#user:hover {
  color: rgba(0, 0, 0, 0.71);
  background-color: transparent;
}

/*.ant-dropdown-open{*/
/*  background-color: rgba(0, 0, 0, 0.67);*/
/*}*/

/*.ant-dropdown-menu-item:hover{*/
/*  background-color: #a51f1f;*/
/*}*/
.ant-dropdown-menu-item-active {
  /*color: white;*/
}

.userinfo {
  width: 200px;
  overflow: hidden;
  float: right;
}

.myLogo {
  height: 20px;
  width: 20px;
  /*filter:Invert();*/
  margin: 10px;
}

#user {
  float: right;
  margin-bottom: 8px;
  margin-right: 4em;
  width: 3em;
  height: 2em;
  /*color: whitesmoke;*/
  font-size: medium;
  font-weight: 500;
  padding-top: 2px;
}
</style>
