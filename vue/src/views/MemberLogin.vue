<template>
  <div class="login-container">
    <!-- 表单 表单验证:rules="rules" -->
    <el-form :model="ruleForm" :rules="rules"
             status-icon
             ref="ruleForm"
             label-position="left"
             label-width="0px"
             class="demo-ruleForm login-page">
      <h1 class="title">会员用户登录</h1>
      <el-form-item prop="adminAccount">
        <el-input type="text"
                  v-model="ruleForm.adminAccount"
                  auto-complete="off"
                  placeholder="用户名"
                  class="input"
        ></el-input>
      </el-form-item>
      <el-form-item prop="adminPassword">
        <el-input type="password"
                  v-model="ruleForm.adminPassword"
                  auto-complete="off"
                  placeholder="密码"
                  @keyup.enter.native="submitForm"
                  class="input"
        ></el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%;" @click="submitForm" class="button">登录</el-button>

      </el-form-item>
      <el-button  style="width:100%;" @click="registerMember" class="button" >注册</el-button>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
import {deleteEmployee, getAllEmployee, getMemberPassword} from "@/api/allApi";

export default {


  data() {
    return {
      identity: '1',
      ruleForm: {
        adminAccount: '',
        adminPassword: ''
      },
      rules: {
        adminAccount: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        adminPassword: [{required: true, message: '请输入密码', trigger: 'blur'}]
      }
    }
  },
  methods: {


    submitForm() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          let _this = this
          let memberPhone = _this.ruleForm.adminAccount
          let memberPassword = _this.ruleForm.adminPassword
          getMemberPassword({
            memberPhone: memberPhone,
            memberPassword: memberPassword
          }).then(res => {
            if (res.data.code === 200) {
              window.localStorage.setItem('access-member', JSON.stringify(res.data))
              _this.$router.replace({path: '/memberLayout'})
            } else {
              this.$message({
                message: '账号或密码错误',
                type: 'error'
              });
            }
          }).catch(err => {
            this.$message.error('操作失败，请稍后重试')
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs.ruleForm.resetFields();
    },
    registerMember(){
      this.$router.replace({path: '/registerMember'})
    }
  }
}

</script>

<style scoped>
.login-container {
  /* width: 100%;
  height: 100%;
  text-align: center; */
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  position: relative;
  background-color: white;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
}

.login-page {
  /* -webkit-border-radius: 5px;
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6; */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50vw;
  min-width: 300px;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  background-color: rgba(0, 0, 0, 0, 0.7);
  border-radius: 15px;
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.5);
}

.title {
  /* margin-bottom: 20px;
  color: #309975; */
  width: 60%;
  margin: 50px auto 0;
  color: rgb(7, 69, 63);
  text-align: center;

}

.input {
  text-align: center;
  padding: 10px;
}

.button {
  background-color: rgba(9, 108, 144, 0.5);
  padding: 10px;
}


</style>
