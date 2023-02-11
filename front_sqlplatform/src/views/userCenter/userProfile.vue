<template>
  <div class="mycontainer">
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
          v-decorator="['name', { rules: [{ required: true, message: '请输入用户名' }],validateTrigger: 'submit' }]"
          v-if="modify"
          class="info-input"
        />
        <span v-else class="infodisplayitem">{{ userInfo.name }}</span>
      </a-form-item>
      <a-form-item
        v-if="modify">
        <div class="itemlabel"> 密码</div>
        <a-input
          type="password"
          placeholder="请输入新密码"
          v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }],validateTrigger: 'submit' }]"
          class="info-input"
        />
      </a-form-item>
      <a-form-item
        v-if="modify">
        <div class="itemlabel"> 确认密码</div>
        <a-input
          type="password"
          placeholder="请再次输入新密码"
          v-decorator="['passwordConfirm', { rules: [{ required: true, message: '请输入新密码' },{ validator: this.handlePasswordCheck }],validateTrigger: 'submit' }]"
          class="info-input"
        />
      </a-form-item>

      <div class="bottom" v-if="modify">
        <a-button shape="round" type="primary" @click="saveModify">
          保存
        </a-button>
        <a-button shape="round" @click="cancelModify" style="margin-left: 10px;">
          取消
        </a-button>
      </div>
      <div class="bottom" v-else>
        <a-button shape="round" type="primary" @click="modifyInfo">
          修改信息
        </a-button>
      </div>
    </a-form>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

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
      'userId',
      'userInfo',
    ]),
  },
  methods: {
    ...mapActions(['modifyUserInfo']),
    handlePasswordCheck(rule, value, callback) {
      const password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },
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
            email: this.userInfo.email,
            password: this.form.getFieldValue('password')
          }
          this.modifyUserInfo(data).then(() => {
            this.modify = false
          })
        }
      });
    },
  }
}
</script>

<style scoped>
#coordinated_name,#coordinated_password,#coordinated_passwordConfirm {
  border-top-color: transparent;
  border-left-color: transparent;
  border-right-color: transparent;
  background-color: transparent;
  /*font-size: 18px;*/
}
#coordinated_name:focus,#coordinated_password:focus,#coordinated_passwordConfirm:focus{
  box-shadow: 0 0 0;
  background-color: transparent;
  /*font-size: 18px;*/
}
/deep/ .ant-input{
  border-radius: 0px;
}
</style>

<style scoped>
.mycontainer {
  text-align: center;
  width: 80vw;
  padding-top: 5em;
}

/*.userdescript {*/
/*  color: #333333;*/
/*  font-size: 15px;*/
/*  text-align: center;*/
/*  margin-bottom: 20px;*/
/*}*/

.userinfotitle {
  color: #333333;
  font-size: 28px;
  margin-top: 20px;
  text-align: left;
/*//margin-left: 25px;*/
}
.userinfodisc {
  color: #999;
  font-size: 16px;
/*//margin-left: 25px;*/
  margin-bottom: 30px;
  margin-top: 10px;
}

.userinfofrom {
  display: inline-block;
  padding: 3em;
  margin: 1em;
  /*//border: 1px solid #d9d9d9;*/
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.11);
  /*//width: 78vw;*/
  height: 78vh;
}
/deep/ .ant-form-item-children {
  padding-bottom: 13px;
  padding-top: 13px;
  border-bottom: 1px solid #d9d9d9;
  display: flex;
  flex-direction: row;
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
  font-size: 16px;
}

.info-input {
  width: 30vw;
}

.infotitleswiper {
  display: flex;
  flex-direction: row;
}

.bottom {
  margin-top: 2em;
}
</style>
