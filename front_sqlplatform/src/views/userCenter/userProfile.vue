<template>
  <!--  <div  class="mycontainer">-->
  <a-form :form="form" class="userinfofrom">
    <div class="infotitleswiper">
      <div>
        <div class="userinfotitle">个人资料</div>
        <div class="userinfodisc">使用本练习系统的其他同学可能会看到部分信息</div>
      </div>
        <img style="margin-top: 20px;margin-left: 5em"
             src="https://www.gstatic.com/identity/boq/accountsettingsmobile/privacycheckup_scene_316x112_3343d1d69c2d68a4bd3d28babd1f9e80.png">
    </div>

    <a-form-item>
      <div class="itemlabel">邮箱</div>
      <span class="infodisplayitem">{{ userInfo.email }}</span>
    </a-form-item>
    <a-form-item>
      <div class="itemlabel">用户名</div>
      <a-input
        placeholder="请填写用户名"
        v-decorator="['name', { rules: [{ required: true, message: '请输入用户名' }] }]"
        v-if="modify"
        class="info-input"
      />
      <span v-else class="infodisplayitem">{{ userInfo.name }}</span>
    </a-form-item>
    <a-form-item
      v-if="modify">
      <div class="itemlabel"> 密码</div>
      <a-input
        placeholder="请输入新密码"
        v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
        class="info-input"
      />
    </a-form-item>

    <div class="bottom" v-if="modify">
      <a-button shape="round" type="primary" @click="saveModify">
        保存
      </a-button>
      <a-button shape="round" style="margin-left: 10px" @click="cancelModify">
        取消
      </a-button>
    </div>
    <div class="bottom" v-else>
      <a-button shape="round" @click="modifyInfo">
        修改信息
      </a-button>
    </div>
  </a-form>
  <!--  </div>-->
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "userProfile",
  data() {
    return {
      form: this.$form.createForm(this, {name: 'coordinated'}),
      modify: false,
      formLayout: 'horizontal',
      pagination: {},
      data: [],

    }
  },
  computed: {
    ...mapGetters([
      // 'userId',
      // 'creditInfo',
      'userInfo',
      // 'userOrderList',
      // 'registeredMemberVisible',
      // 'userpic'
    ]),

  },
  methods: {
    modifyInfo() {
      setTimeout(() => {
        this.form.setFieldsValue({
          'name': this.userInfo.name,
          'email': this.userInfo.email,
        })
      }, 0)
      this.modify = true
    },
    cancelModify() {
      this.modify = false
    },
    saveModify() {
      this.form.validateFields((err, values) => {
        if (!err) {
          const data = {
            name: this.form.getFieldValue('name'),
            email: this.form.getFieldValue('email'),
            password: this.form.getFieldValue('password')
          }
          console.log(data)
          this.updateUserInfo(data).then(() => {
            this.modify = false
          })
        }
      });
    },
  }
}
</script>

<style lang="less">

.userdescript {
  color: #333333;
  font-size: 15px;
  text-align: center;
  margin-bottom: 20px;
}

.userinfofrom {
  padding: 3em;
  margin: .6em;
  //border: 1px solid #d9d9d9;
  //box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
  //width: 78vw;
  height: calc(98vh - 47px);
  //border-radius: 8px;
  .userinfotitle {
    color: #333333;
    font-size: 28px;
    margin-top: 20px;
    //text-align: left;
    margin-left: 25px;
  }

  .userinfodisc {
    color: #999;
    font-size: 16px;
    //margin-left: 25px;
    margin-bottom: 30px;
    margin-top: 10px;
  }

  .ant-form-item {
    margin-bottom: 10px;
  }

  .ant-form-item-children {
    padding-bottom: 13px;
    padding-top: 13px;
    border-bottom: 1px solid #d9d9d9;
    display: flex;
    flex-direction: row;
    //margin-left: 30px;
    //width: 668px;
  }
}

.myicon {
  position: absolute;
  left: 620px;
}

.itemlabel {
  width: 190px;
  color: #999;
}

.infodisplayitem {
  color: #333333;
  font-size: 18px;
}

.info-input {
  width: 30vw;
}

.infotitleswiper {
  display: flex;
  flex-direction: row;
}
.bottom{
  margin: 1em 0 2em 5em;
  float: left;
}
</style>
