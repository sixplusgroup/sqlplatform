<template>
  <div class="main">
    <div class="login-left">
      <h1 style="font-weight: bolder;font-size: 50px">SQL Platform</h1>
      <a-form
        id="formLogin"
        class="user-layout-login"
        ref="formLogin"
        :form="form"
      >
        <a-tabs
          :activeKey="customActiveKey"
          :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
          @change="handleTabClick"
        >
          <a-tab-pane key="tab1" tab="登录">
            <a-form-item class="formItem">
              <a-input class="login-input"
                       size="large"
                       type="text"
                       placeholder="电子邮件地址"
                       v-decorator="[
                'email',
                {rules: [{ required: true, message: '请输入邮箱' }], validateTrigger: 'submit'}
              ]"
                       style="margin-bottom: .5em;margin-top: 2em;"
              >
                <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>
            <a-form-item class="formItem">
              <a-input class="login-input"
                       size="large"
                       type="password"
                       autocomplete="false"
                       placeholder="密码"
                       v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入密码' },],validateTrigger: 'submit'}
              ]"
                       style="margin-bottom: .5em"
              >
                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>
            <a-form-item class="formItem" style="margin-top: 5em">
              <a-button
                size="large"
                type="primary"
                class="login-button"
                :loading="loginLoading"
                @click="handlelogin()"
                style="margin-bottom: 1em"
              >确定
              </a-button>
              <a @click="forgotPassword">忘记密码</a>
              <a-modal
                title="忘记密码"
                :visible="forgotPasswordVisible"
                @ok="handleForgotPasswordOk"
                :ok-button-props="{ props: {shape: 'round'}}"
                :cancel-button-props="{ props: {shape: 'round'}}"
                @cancel="handleForgotPasswordCancel"
                style="padding:2em"
              >
                <a-form-item>
                  <a-input-search class="login-input" placeholder="电子邮件地址"
                                  readOnly
                                  onfocus="this.removeAttribute('readonly');"
                                  onblur="this.setAttribute('readonly',true);"
                                  @search="handleCodeSending" style="margin-bottom: 3px"
                                  v-decorator="['resetEmail',{ rules: [{ required: true,
                                   pattern: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,
                                   message: '请输入正确格式的邮箱' }],validateTrigger: 'submit'}]">
                    <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                    <template #enterButton>
                      <a-button :disabled="codeIsSend">{{ codeName }}</a-button>
                    </template>
                  </a-input-search>
                </a-form-item>
                <a-form-item class="register-formItem">
                  <a-input class="login-input"
                           placeholder="验证码"
                           readOnly
                           onfocus="this.removeAttribute('readonly');"
                           onblur="this.setAttribute('readonly',true);"
                           v-decorator="[
              'resetCode',
              {rules: [{ required: true, message: '请输入邮箱验证码' }],validateTrigger: 'submit'}]"
                           style="margin-bottom: 3px">
                    <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                  </a-input>
                </a-form-item>
                <a-form-item class="register-formItem">
                  <a-input class="login-input"
                           type="password"
                           placeholder="密码"
                           readOnly
                           onfocus="this.removeAttribute('readonly');"
                           onblur="this.setAttribute('readonly',true);"
                           v-decorator="[
                'resetPassword',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }],validateTrigger: 'submit'}]"
                           style="margin-bottom: 3px">
                    <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                  </a-input>
                </a-form-item>

                <a-form-item class="register-formItem">
                  <a-input class="login-input"
                           type="password"
                           placeholder="确认密码"
                           readOnly
                           onfocus="this.removeAttribute('readonly');"
                           onblur="this.setAttribute('readonly',true);"
                           v-decorator="[
                'resetPasswordConfirm',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handleResetPasswordCheck }],validateTrigger: 'submit'}]"
                           style="margin-bottom: 3px">
                    <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                  </a-input>
                </a-form-item>


              </a-modal>
            </a-form-item>

          </a-tab-pane>

          <a-tab-pane key="tab2" tab="注册">
            <a-form-item class="register-formItem">
              <a-input-search class="login-input"
                              size="large"
                              placeholder="电子邮件地址"
                              readOnly
                              onfocus="this.removeAttribute('readonly');"
                              onblur="this.setAttribute('readonly',true);"
                              @search="handleCodeSending"
                              v-decorator="[
              'registerEmail',
              {rules: [{
               required: true,
               pattern: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,
               message: '请输入正确格式的邮箱' }],
               validateTrigger: 'submit'}]"
                              style="margin-bottom: 3px"
              >
                <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }"/>
<!--                <template #enterButton>-->
                  <a-button :disabled="codeIsSend" slot="enterButton" >{{ codeName }}</a-button>
<!--                </template>-->
              </a-input-search>
            </a-form-item>
            <a-form-item class="register-formItem">
              <a-input class="login-input"
                       size="large"
                       placeholder="验证码"
                       v-decorator="[
              'registerCode',
              {rules: [{ required: true, message: '请输入邮箱验证码' }], validateTrigger: 'submit'}]"
                       style="margin-bottom: 3px">
                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>
            <a-form-item class="register-formItem">
              <a-input class="login-input"
                       size="large"
                       readOnly
                       onfocus="this.removeAttribute('readonly');"
                       onblur="this.setAttribute('readonly',true);"
                       placeholder="用户名"
                       v-decorator="[
              'registerUsername',
              {rules: [{ required: true, message: '请输入用户名' }], validateTrigger: 'submit'}]"
                       style="margin-bottom: 3px">
                <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>
            <a-form-item class="register-formItem">
              <a-input class="login-input"
                       size="large"
                       type="password"
                       readOnly
                       onfocus="this.removeAttribute('readonly');"
                       onblur="this.setAttribute('readonly',true);"

                       placeholder="密码"
                       v-decorator="[
                'registerPassword',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }], validateTrigger: 'submit'}]"
                       style="margin-bottom: 3px">
                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>
            <a-form-item class="register-formItem">
              <a-input class="login-input"
                       size="large"
                       type="password"
                       readOnly
                       onfocus="this.removeAttribute('readonly');"
                       onblur="this.setAttribute('readonly',true);"
                       placeholder="确认密码"
                       v-decorator="[
                'registerPasswordconfirm',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePasswordCheck }],validateTrigger: 'submit'}]"
                       style="margin-bottom: 3px">
                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>
            <a-form-item class="register-formItem" style="margin-top: 3em">
              <a-button
                size="large"
                type="primary"
                class="login-button"
                :loading="registerLoading"
                @click="handleRegister()"
              >确认注册
              </a-button>
            </a-form-item>
          </a-tab-pane>
        </a-tabs>
      </a-form>
    </div>
    <div class="login-right">
    </div>


  </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex'
import {message} from 'ant-design-vue'


export default {
  name: 'login',
  components: {},
  data() {
    return {
      customActiveKey: 'tab1',
      loginLoading: false,
      registerLoading: false,
      form: this.$form.createForm(this),
      forgotPasswordVisible: false,
      //验证码发送按钮倒计时
      codeIsSend: false,
      restTime: 60,
      codeName: "发送验证码",
      timer: ''
    }
  },
  computed: {
    ...mapGetters([
      'token',
    ])
  },
  mounted() {

  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    },
  },
  methods: {
    ...mapActions([
      'login',
      'register',
      'sendCode',
      'resetPassword'
    ]),
    forgotPassword() {
      this.forgotPasswordVisible = true;
    },
    handleForgotPasswordOk(e) {
      const validateFieldsKey = ['resetEmail', 'resetCode', 'resetPassword', 'resetPasswordConfirm'];
      this.form.validateFields(validateFieldsKey, {force: true}, async (err, values) => {
        if (!err) {
          const data = {
            email: this.form.getFieldValue('resetEmail'),
            code: this.form.getFieldValue('resetCode'),
            password: this.form.getFieldValue('resetPassword'),
            passwordConfirmation: this.form.getFieldValue('resetPasswordConfirm'),
          }
          await this.resetPassword(data).then(() => {
            this.form.setFieldsValue({
              'resetEmail': '',
              'resetCode': '',
              'resetPassword': '',
              'resetPasswordConfirm': ''
            })
          })
          this.forgotPasswordVisible = false;
        }
      })

    },
    handleForgotPasswordCancel() {
      this.forgotPasswordVisible = false;
    },
    handlePassword(rule, value, callback) {
      if (value.length < 6) {
        // callback(new Error('密码长度至少6位'))
      }
      callback()
    },
    handlePasswordCheck(rule, value, callback) {
      const password = this.form.getFieldValue('registerPassword')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },
    handleResetPasswordCheck(rule, value, callback) {
      const password = this.form.getFieldValue('resetPassword')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },
    handleTabClick(key) {
      this.customActiveKey = key
    },
    handlelogin() {
      const validateFieldsKey = this.customActiveKey === 'tab1' ? ['email', 'password'] : ['registerEmail', 'registerUsername', 'registerPassword', 'registerPasswordconfirm']
      this.form.validateFields(validateFieldsKey, {force: true}, async (err, values) => {
        if (!err) {
          this.loginLoading = true;
          // let formData = new FormData();
          // formData.append("email", this.form.getFieldValue("email"));
          // formData.append("password", this.form.getFieldValue("password"));
          const data = {
            email: this.form.getFieldValue('email'),
            password: this.form.getFieldValue('password'),
          };
          await this.login(data);
          this.loginLoading = false
        }
      })
    },
    handleCodeSending(email) {
      if (this.codeIsSend) return
      let reg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
      if (!reg.test(email)) {
        message.error("邮箱格式不正确！")
        return
      }
      this.sendCode(email)
      this.codeIsSend = true
      this.codeName = this.restTime + 's后可重新发送'
      this.timer = setInterval(() => {
        this.restTime--
        this.codeName = this.restTime + 's后可重新发送'
        if (this.restTime < 0) {
          clearInterval(this.timer)
          this.codeName = '重新发送验证码'
          this.restTime = 60
          this.codeIsSend = false
        }
      }, 1000)
    },
    handleRegister() {
      const {form: {validateFields}} = this
      const validateFieldsKey = this.customActiveKey === 'tab1' ? ['username', 'password'] : ['registerEmail', 'registerUsername', 'registerPassword', 'registerPasswordconfirm']
      validateFields(validateFieldsKey, {force: true}, async (err, values) => {
        if (!err) {
          this.registerLoading = true
          const data = {
            email: this.form.getFieldValue('registerEmail'),
            name: this.form.getFieldValue('registerUsername'),
            code: this.form.getFieldValue('registerCode'),
            password: this.form.getFieldValue('registerPassword'),
            passwordConfirmation: this.form.getFieldValue('registerPasswordconfirm'),
          };
          await this.register(data).then(() => {
            // this.customActiveKey = 'tab1';
            this.form.setFieldsValue({
              'registerEmail': '',
              'registerUsername': '',
              'registerPassword': '',
              'registerCode': '',
              'registerPasswordconfirm': ''
            })
          })
          this.registerLoading = false
          this.customActiveKey = 'tab1'
        }
      });
    }
  }
}
</script>

<style>
#email,#password,
#registerEmail,#registerUsername,#registerPassword,#registerPasswordconfirm,#registerCode,
#resetCode,#resetEmail,#resetPassword,#resetPasswordConfirm {
  border-top-color: transparent;
  border-left-color: transparent;
  border-right-color: transparent;
  background-color: transparent;
}
#email:focus,#password:focus,#registerEmail:focus,#registerUsername:focus,
#registerPassword:focus,#registerPasswordconfirm:focus,#registerCode:focus,
#resetPassword:focus,#resetPasswordConfirm:focus,#resetCode:focus,#resetEmail:focus {
  box-shadow: 0 0 0;
  background-color: transparent;
}
</style>
<style scoped>
/deep/ .ant-input-search-button{
  background-color: transparent;
}
/deep/ .ant-input-group-addon{
  background-color: transparent;

}
/deep/ .ant-input{
  border-radius: 0px;
  background-clip: content-box;
  height: 0;
  padding: 1.2em .5em;
  /*box-shadow: 0 0 0px 1000px rgba(255, 255, 255, 0.91) inset;*/
}
</style>

<style lang="less" scoped>

.main {
  min-width: 260px;
  width: 100vw;
  height: 100vh;
  text-align: center;
  /*padding-top: 5em;*/
  background-image: url('../assets/bg.jpeg');
  background-repeat: no-repeat;
  overflow: hidden;
  position: fixed;
  background-position: 0px 0px;
  background-size: 100% 100%;
}

.formItem {
  margin-top: 3em;
  margin-bottom: 3em;
}

.register-formItem {
  margin-top: 2em;
  //margin-bottom: -1em;
}

.login-left {
  background: rgba(255, 255, 255, 0.91);

  padding: 40px 50px;
  margin-left: 5em;

  width: 560px;
  height: 100vh;
  box-shadow: 0 5px 5px rgba(0, 0, 0, .4);

}

.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    /*height: 40px;*/
    height: 100%;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
