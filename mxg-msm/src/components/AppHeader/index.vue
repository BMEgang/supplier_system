<template>
  <div>
    <div class="header">
      <a href="#/">
        <img class="logo" src="@/assets/logo.png" width="25px">
        <span class="company">manage system</span>
      </a>

      <el-dropdown @command="handleCommand">
        <el-button type="primary">
          {{user.name}}<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-edit" command="a">change password</el-dropdown-item>
          <el-dropdown-item icon="el-icon-s-fold" command="b">log out</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dialog title="change pwd" :visible.sync="dialogFormVisible" width="400px">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="120px" style="width: 300px">
          <el-form-item label="Password" prop="oldpass">
            <el-input type="password" v-model="ruleForm.oldpass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="New Password" prop="newpass">
            <el-input type="password" v-model="ruleForm.newpass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="checkPass" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">Submit</el-button>
            <el-button @click="resetForm('ruleForm')">Reset</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

    </div>

  </div>
</template>

<script>
import {logout} from '@/api/login'
import passwordApi from '@/api/password'
export default {
  data(){
    const validateOldPass = (rule, value, callback) =>{
      passwordApi.checkPwd(this.user.id, value).then(response =>{
        const resp = response.data
        if (resp.flag){
          callback();
        }else{
          callback(new Error(resp.message));
        }
      })
    }

    const validatePass = (rule, value, callback) => {
      if (value != this.ruleForm.newpass){
        callback(new Error('两次输入的密码不一致'))
      }else{
        callback()
      }
    }

    return {
      user: JSON.parse(localStorage.getItem('mxg-msm-user')),
      dialogFormVisible: false,
      ruleForm: {
        oldpass: '',
        newpass: '',
        checkPass: '',
      },
      rules: {
        oldpass: [
          {required: true, message: "cardNum cannot be empty", trigger: 'blur'},
          { validator: validateOldPass, trigger: 'blur' }
        ],
        newpass: [
          {required: true, message: "name cannot be empty", trigger: 'blur'}
        ],
        checkPass: [
          {required: true, message: "checkPass cannot be empty", trigger: 'blur'},
          { validator: validatePass, trigger: 'change' }
        ],
      },
    }
  },


  methods: {
    handleCommand(command) {

      switch (command) {
          case 'a':
            this.handlePwd()
            break;
          case 'b':
            this.handleLogout()
            break;
          default:
            break;
      }
    },
    handleLogout(){
      const token = localStorage.getItem('mxg-msm-token')
      logout(token).then(response => {
        const resp = response.data
        if (resp.flag){
          localStorage.removeItem('mxg-msm-token')
          localStorage.removeItem('mxg-msm-user')
          this.$router.push('/login')
        }else {
          this.$message({
            message: resp.message,
            type: 'warning',
            duration: 500
          })
        }
      })
    },
    handlePwd(){
      this.dialogFormVisible=true
      if (this.$refs['ruleForm']!==undefined) {
        this.$refs['ruleForm'].resetFields()
      }
    },
    resetForm(formName) {
      console.log("重置",formName)
      this.$refs[formName].resetFields();
    },
    submitForm(formName){
      this.$refs[formName].validate(valid =>{
        if (valid){
          passwordApi.updatePwd(this.user.id, this.ruleForm.checkPass).then(response =>{
            const resp = response.data

            this.$message({
              message: resp.message,
              types: resp.flag ? 'success' : 'warning'
            })

            if (resp.flag){
              this.handleLogout()
              this.dialogFormVisible = false
            }
          })
        }else{
          return false
        }
      })
    }
  }
}
</script>

<style>
.logo{
  vertical-align: middle;
  padding: 0px 10px 0 40px;
}
.company{
  position: absolute;
  color: white;
}
.el-dropdown{
  float: right;
  margin-right: 40px;
}
</style>